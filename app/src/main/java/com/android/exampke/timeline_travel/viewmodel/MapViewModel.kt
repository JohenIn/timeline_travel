package com.android.exampke.timeline_travel.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import timber.log.Timber

class MapViewModel: ViewModel() {
    // State to hold the user's location as LatLng (latitude and longitude)
    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    fun fetchUserLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) {
        // Check if the location permission is granted
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                // Fetch the last known location
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        // Update the user's location in the state
                        val userLatLng = LatLng(it.latitude, it.longitude)
                        _userLocation.value = userLatLng
                    }
                }
            } catch (e: SecurityException) {
                Timber.e("Permission for location access was revoked: ${e.localizedMessage}")
            }
        } else {
            Timber.e("Location permission is not granted.")
        }
    }
}
@Composable
fun ShowGoogleMap(mapViewModel: MapViewModel, modifier: Modifier) {
    // Initialize the camera position state, which controls the camera's position on the map
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    val userLocation by mapViewModel.userLocation
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Handle permission requests for accessing fine location
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Fetch the user's location and update the camera if permission is granted
            mapViewModel.fetchUserLocation(context, fusedLocationClient)
        } else {
            // Handle the case when permission is denied
            Timber.e("Location permission was denied by the user.")
        }
    }
    // 랜드마크 리스트 정의
//    val landmarks = listOf(
//        LatLng(37.5511694, 126.9882266) to "Namsan Tower", // 남산타워
//        LatLng(37.5194207, 126.9404655) to "63 Building"   // 63빌딩
//    )

    // google map url로 넣기
    val placesUrls = listOf(
        "https://www.google.co.kr/maps/place/%EC%9D%B4%EC%88%9C%EC%8B%A0+%EC%9E%A5%EA%B5%B0+%EB%8F%99%EC%83%81/data=!3m1!4b1!4m6!3m5!1s0x357ca2ec98800045:0xdd5786518f45a705!8m2!3d37.5710015!4d126.9769419!16s%2Fg%2F11g6rkhdqc?entry=ttu" to "Namsan Tower",
        "https://www.google.co.kr/maps/place/%EC%9D%B4%EC%88%9C%EC%8B%A0+%EC%9E%A5%EA%B5%B0+%EB%8F%99%EC%83%81/data=!3m1!4b1!4m6!3m5!1s0x357ca2ec98800045:0xdd5786518f45a705!8m2!3d37.5740015!4d126.9799419!16s%2Fg%2F11g6rkhdqc?entry=ttu" to "63 Building",
        "https://www.google.co.kr/maps/place/%EC%9D%B4%EC%88%9C%EC%8B%A0+%EC%9E%A5%EA%B5%B0+%EB%8F%99%EC%83%81/data=!3m1!4b1!4m6!3m5!1s0x357ca2ec98800045:0xdd5786518f45a705!8m2!3d37.5790015!4d126.9829419!16s%2Fg%2F11g6rkhdqc?entry=ttu" to "Dongdaemun Design Plaza"
    )

    val markers = remember { mutableStateOf<List<Pair<LatLng, String>>>(emptyList()) }

    // URL에서 위도와 경도 추출하는 함수
    fun extractLatLngFromUrl(url: String): LatLng? {
        val regex = """3d(-?\d+\.\d+).*4d(-?\d+\.\d+)""".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.let {
            val latitude = it.groupValues[1].toDouble()
            val longitude = it.groupValues[2].toDouble()
            LatLng(latitude, longitude)
        }
    }
    LaunchedEffect(Unit) {
        // placesUrls에서 위도와 경도를 추출하여 마커로 추가
        val resolvedMarkers = placesUrls.mapNotNull { (url, name) ->
            extractLatLngFromUrl(url)?.let { location ->
                location to name
            }
        }
        markers.value = resolvedMarkers
    }

// Request the location permission when the composable is launched
    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            // Check if the location permission is already granted
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Fetch the user's location and update the camera
                mapViewModel.fetchUserLocation(context, fusedLocationClient)
            }
            else -> {
                // Request the location permission if it has not been granted
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }


    // Display the Google Map without
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ){
        // If the user's location is available, place a marker on the map
        userLocation?.let {
            Marker(
                state = MarkerState(position = it), // Place the marker at the user's location
                title = "Your Location", // Set the title for the marker
                snippet = "This is where you are currently located." // Set the snippet for the marker
            )
            // Move the camera to the user's location with a zoom level of 10f
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 16f)
        }
        // 마커 추가 -> 지도 위에 위치 띄워주는 거 해야됨
        markers.value.forEach { (location, name) ->
            Marker(
                state = MarkerState(position = location),
                title = name,
                onClick = {
                    // 마커 클릭 시 구글맵에서 장소 열기
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(placesUrls.first { it.second == name }.first))
                    context.startActivity(intent)
                    true
                }
            )
        }
    }
}