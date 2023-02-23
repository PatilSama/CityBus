package com.example.citybus;

import static com.google.android.gms.maps.CameraUpdateFactory.*;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectionsAPICall extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directioncall_api_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        direction();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(18.467384, 73.864485);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
    }

    private void direction()
    {
     //   double[] destLat = {18.489014},destLong = {73.814322};
        double[] destLat = {18.500163},destLong = { 73.858471};
        double[] startLat ={18.467384} ,startLong = {73.864485};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Uri.parse("https://maps.googleapis.com/maps/api/directions/json").buildUpon()
                .appendQueryParameter("destination",destLat[0]+","+destLong[0])
                .appendQueryParameter("origin",startLat[0]+","+startLong[0])
                .appendQueryParameter("mode","driving") // walking
                .appendQueryParameter("key","AIzaSyCB7Xz7_9bZc5OCPDjzjOW7NbvVPMocYfs").toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                //    Toast.makeText(DirectionsAPICall.this, ""+status, Toast.LENGTH_SHORT).show();
                    if(status.equals("OK"))
                    {
                        JSONArray routes = response.getJSONArray("routes");
                        ArrayList<LatLng> points;
                        PolylineOptions polylineOptions = null;
//                        Log.d("Response = ", String.valueOf(routes));
                        for(int i=0;i<routes.length();i++)
                        {
                            points = new ArrayList<>();
                            polylineOptions = new PolylineOptions();
                            JSONArray legs = routes.getJSONObject(i).getJSONArray("legs");

                            for(int j=0;j<legs.length();j++)
                            {
                                JSONArray steps = legs.getJSONObject(j).getJSONArray("steps");

                                for(int k=0;k<steps.length();k++)
                                {
                                    String polyline = steps.getJSONObject(k).getJSONObject("polyline").getString("points");
                                    // Calling Decode Polyline Method.
                                    List<LatLng> list  = decodePoly(polyline);

                                    for(int l=0;l<list.size();l++)
                                    {
//                                        Log.d("Latitude", String.valueOf(list.get(l).latitude));
//                                        Log.d("Longitude", String.valueOf(list.get(l).longitude));
                                        points.add(new LatLng((list.get(l)).latitude,(list.get(l)).longitude));
                                    }
                                }
                            }
                            polylineOptions.addAll(points);
                            polylineOptions.width(13);
                            polylineOptions.color(Color.BLACK);
                            polylineOptions.geodesic(true);
                        }

                        List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dot(), new Gap(10f));
                        Polyline polyline = mMap.addPolyline(polylineOptions);
                        polyline.setPattern(pattern);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(destLat[0],destLong[0])).title("HERE"));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(startLat[0], startLong[0])).title("HERE"));

                        LatLngBounds bounds = new LatLngBounds.Builder()
                                .include(new LatLng(destLat[0],destLong[0]))
                                .include(new LatLng(startLat[0], startLong[0])).build();

                        Point point = new Point();
                        getWindowManager().getDefaultDisplay().getSize(point);
                        mMap.animateCamera(newLatLngBounds(bounds,point.x,150,30));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Direction API Error = ",error.getMessage());
                Toast.makeText(DirectionsAPICall.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RetryPolicy retryPolicy = new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonObjectRequest);
    }

    // Decode Polyline
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b > 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}