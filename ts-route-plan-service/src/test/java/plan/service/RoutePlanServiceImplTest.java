package plan.service;

import edu.fudan.common.entity.Trip;
import edu.fudan.common.entity.TripResponse;
import edu.fudan.common.entity.TripAllDetail;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import edu.fudan.common.entity.RoutePlanInfo;
import edu.fudan.common.entity.Route;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class RoutePlanServiceImplTest {

    @InjectMocks
    private RoutePlanServiceImpl routePlanServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchCheapestResult() {
        RoutePlanInfo info = new RoutePlanInfo("form_station", "to_station", "", 1);
        //mock getTripFromHighSpeedTravelServive() and getTripFromNormalTrainTravelService()
        ArrayList<TripResponse> tripResponses = new ArrayList<>();
        Response<ArrayList<TripResponse>> response1 = new Response<>(null, null, tripResponses);
        ResponseEntity<Response<ArrayList<TripResponse>>> re1 = new ResponseEntity<>(response1, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re1);
        Response result = routePlanServiceImpl.searchCheapestResult(info, headers);
        Assert.assertEquals(new Response<>(1, "Success", new ArrayList<>()), result);
    }

    @Test
    public void testSearchQuickestResult() {
        RoutePlanInfo info = new RoutePlanInfo("form_station", "to_station", "", 1);
        //mock getTripFromHighSpeedTravelServive() and getTripFromNormalTrainTravelService()
        ArrayList<TripResponse> tripResponses = new ArrayList<>();
        Response<ArrayList<TripResponse>> response1 = new Response<>(null, null, tripResponses);
        ResponseEntity<Response<ArrayList<TripResponse>>> re1 = new ResponseEntity<>(response1, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re1);
        Response result = routePlanServiceImpl.searchQuickestResult(info, headers);
        Assert.assertEquals(new Response<>(1, "Success", new ArrayList<>()), result);
    }

    @Test
    public void testSearchMinStopStations() {
        RoutePlanInfo info = new RoutePlanInfo("form_station", "to_station", "", 1);

        ArrayList<Route> routeArrayList = new ArrayList<>();
        Response<ArrayList<Route>> response = new Response<>(null, null, routeArrayList);
        ResponseEntity<Response<ArrayList<Route>>> re = new ResponseEntity<>(response, HttpStatus.OK);

        ArrayList<ArrayList<Trip>> tripLists = new ArrayList<>();
        Response<ArrayList<ArrayList<Trip>>> response2 = new Response<>(null, null, tripLists);
        ResponseEntity<Response<ArrayList<ArrayList<Trip>>>> re2 = new ResponseEntity<>(response2, HttpStatus.OK);

        TripAllDetail tripAllDetail = new TripAllDetail();
        Response<TripAllDetail> response3 = new Response<>(null, null, tripAllDetail);
        ResponseEntity<Response<TripAllDetail>> re3 = new ResponseEntity<>(response3, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.any(HttpMethod.class),
                Mockito.any(HttpEntity.class),
                Mockito.any(ParameterizedTypeReference.class)))
                .thenReturn(re).thenReturn(re2).thenReturn(re2).thenReturn(re2).thenReturn(re3);
        Response result = routePlanServiceImpl.searchMinStopStations(info, headers);
        Assert.assertEquals("Success.", result.getMsg());
    }

}
