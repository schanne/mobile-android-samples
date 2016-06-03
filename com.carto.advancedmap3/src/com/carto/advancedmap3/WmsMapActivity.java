package com.carto.advancedmap3;

import android.os.Bundle;

import com.carto.advancedmap3.datasource.HttpWmsTileDataSource;
import com.carto.core.MapPos;
import com.carto.layers.RasterTileLayer;

/**
 * A sample demonstrating how to use WMS service raster on top of
 * the vector base map
 */
public class WmsMapActivity extends VectorMapSampleBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // MapSampleBaseActivity creates and configures mapView  
        super.onCreate(savedInstanceState);


        // USGS Base map: http://basemap.nationalmap.gov/arcgis/rest/services/USGSTopo/MapServer
        String url = "http://basemap.nationalmap.gov/arcgis/services/USGSTopo/MapServer/WmsServer?";
        String layers = "0";

        HttpWmsTileDataSource wms = new HttpWmsTileDataSource(0, 14, baseProjection, false, url, "", layers, "image/png8");
        RasterTileLayer wmsLayer = new RasterTileLayer(wms);

        // Calculate zoom bias, basically this is needed to 'undo' automatic DPI scaling, we will display original raster with close to 1:1 pixel density
        double zoomLevelBias = Math.log(mapView.getOptions().getDPI() / 160) / Math.log(2);
        wmsLayer.setZoomLevelBias((float) zoomLevelBias);

        mapView.getLayers().add(wmsLayer);
        
        // finally animate map to map coverage
        mapView.setFocusPos(baseProjection.fromWgs84(new MapPos(-100, 40)), 1);
        mapView.setZoom(5, 1);
    }
}
