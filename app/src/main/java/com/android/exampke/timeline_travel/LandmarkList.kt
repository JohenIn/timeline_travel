package com.android.exampke.timeline_travel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Landmark(
    val images: String,
    val name: String,
    val location: String,
    val history: String,
    val recentNews: String,
    val youTubeVideoId: String?,
    val address: String,
    val openingHours: String,
    val googleMapUrl: String,
    var isFavorited: Boolean = true // 즐겨찾기 여부 상태 추가
) : Parcelable

val landmarks = listOf(
    Landmark(
        images = "https://www.63realty.co.kr/upload/202007/0cf43966-cf30-420f-bba2-b4c871e4bc8a.jpg",
        name = "63빌딩",
        location = "서울시 영등포구",
        history = "한 때 가장 높은",
        recentNews = "잘 모르겠음",
        youTubeVideoId = null,
        address = "서울 영등포구 63로 50 한화금융센터_63",
        openingHours = "24시",
        googleMapUrl = "https://www.google.com/maps/place/63+Building/data=!3m1!4b1!4m6!3m5!1s0x357c9f3c1822f59f:0x81e51bac4441f659!8m2!3d37.5198332!4d126.9400724!16zL20vMDZyYmd4?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://newsimg.sedaily.com/2018/06/22/1S0WPLM2WE_17.jpg",
        name = "이순신장군동상",
        "서울시 종로구",
        "멋진 장군님",
        "잘 모르겠움,",
        null,
        "서울 종로구 세종대로 172",
        "24시",
        googleMapUrl = "https://www.google.com/maps/place/Statue+of+Admiral+Yi+Sun-sin/data=!4m14!1m7!3m6!1s0x357ca2ec98800045:0xdd5786518f45a705!2sStatue+of+Admiral+Yi+Sun-sin!8m2!3d37.5710015!4d126.9769419!16s%2Fg%2F11g6rkhdqc!3m5!1s0x357ca2ec98800045:0xdd5786518f45a705!8m2!3d37.5710015!4d126.9769419!16s%2Fg%2F11g6rkhdqc?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://i.namu.wiki/i/DK-BcaE6wDCM-N9UJbeQTn0SD9eWgsX9YKWK827rqjbrzDz0-CxW-JFOCiAsUL3CBZ4zE0UDR-p4sLaYPiUjww.webp",
        name = "남산타워",
        location = "서울시 종로구",
        history = "멋진 타워",
        recentNews = "잘 모르겠음 ytn",
        null,
        "서울 용산구 남산공원길 105",
        "24시",
        googleMapUrl = "https://www.google.com/maps/place/N+Seoul+Tower/data=!3m1!4b1!4m6!3m5!1s0x357ca257a88e6aa9:0x5cf8577c2e7982a5!8m2!3d37.5511694!4d126.9882266!16zL20vMDJxcGYx?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://newsroom.posco.com/kr/wp-content/uploads/2019/07/lotte.png",
        name = "롯데월드타워",
        location = "서울시 송파구",
        history = "엄청 높은 타워",
        recentNews = "롯데가 롯데월드 옆에 지은 짱 높은 타워",
        youTubeVideoId = null,
        address = "서울 송파구 올림픽로 300",
        openingHours = "09:00 ~ 20:30",
        googleMapUrl = "https://www.google.com/maps/place/LOTTE+WORLD+TOWER/data=!3m1!4b1!4m6!3m5!1s0x357ca50a915f665b:0xabeb10cd5efdfce2!8m2!3d37.5124641!4d127.102543!16s%2Fg%2F11by_z1kf2?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://i.namu.wiki/i/AUpk8QHygUJremQinbr7w6YT4BochQWlBBJ-UwY-WWjZ4_5l6-jjIkT7GT-Xd_EtMjzVdpus3HBemhyNlbEH4w.webp",
        name = "부산타워",
        location = "부산 중구",
        history = "부산에 있는 타워",
        recentNews = "부산에 있는 엄청 높은 타워",
        youTubeVideoId = null,
        address = "부산 중구 용두산길 37-30",
        openingHours = "10:00 ~ 22:00",
        googleMapUrl = "https://www.google.com/maps/place/Busan+Tower/data=!4m10!1m2!2m1!1z67aA7IKw7YOA7JuM!3m6!1s0x3568e974db8343ab:0xd25a159d0652ff4e!8m2!3d35.1011934!4d129.0323676!15sCgzrtoDsgrDtg4Dsm4xaDyIN67aA7IKwIO2DgOybjJIBEG9ic2VydmF0aW9uX2RlY2vgAQA!16s%2Fm%2F0fqrf7s?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://image.kmib.co.kr/online_image/2022/0318/2022031809301240453_1647563412_0016881139.jpg",
        name = "83타워",
        location = "대구 달서구",
        history = "대구에 있는 타워",
        recentNews = "대구에 있는 엄청 높은 타워",
        youTubeVideoId = null,
        address = "대구 달서구 두류공원로 200 83타워",
        openingHours = "11:00 ~ 20:30",
        googleMapUrl = "https://www.google.com/maps/place/Daegu+83+Tower/data=!3m1!4b1!4m6!3m5!1s0x3565e480574187d9:0xf93f6ce0fde7999!8m2!3d35.8533043!4d128.5665671!16s%2Fm%2F0gwzphh?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8PbRF4RzBw0Ckk0x5a0X2CQyQDjZlaMKypA&s",
        name = "인천대교",
        location = "인천 중구",
        history = "인천에 있는 대교",
        recentNews = "인천에 있는 엄청 긴 대교",
        youTubeVideoId = null,
        address = "인천 중구 인천대교고속도로 3 인천대교영업소및사무소",
        openingHours = "24시",
        googleMapUrl = "https://www.google.com/maps/place/Incheon+Bridge/data=!3m1!4b1!4m6!3m5!1s0x357b9d019b658257:0xd26cd0e96d90acc2!8m2!3d37.4136893!4d126.5665206!16s%2Fm%2F02q9yrr?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://www.siminsori.com/news/photo/200701/55580-2-12570.jpg",
        name = "518민주묘지상징탑",
        location = "광주 북구",
        history = "광주에 있는 상징탑",
        recentNews = "광주에 있는 엄청 높은 상징탑",
        youTubeVideoId = null,
        address = "광주 북구 민주로 200 국립5.18민주묘지",
        openingHours = "09:00 ~ 18:00",
        googleMapUrl = "https://www.google.com/maps/place/May+18th+National+Cemetery/data=!3m1!4b1!4m6!3m5!1s0x357192c4573c905f:0xbc08e0b4883c5030!8m2!3d35.2353434!4d126.9401187!16s%2Fm%2F01270nh2?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxfWpffTNtVTyNci9cC4AxZXOoODQOEor7uQ&s",
        name = "한빛탑",
        location = "대전 유성구",
        history = "대전에 있는 한빛탑",
        recentNews = "대전에 있는 엄청 높은 한빛탑",
        youTubeVideoId = null,
        address = "대전 유성구 대덕대로 480",
        openingHours = "09:30 ~ 17:20",
        googleMapUrl = "https://www.google.com/maps/place/EXPO+Hanbit+Tower/data=!3m1!4b1!4m6!3m5!1s0x356549860756d7af:0x5bb388ce6c184d60!8m2!3d36.3765465!4d127.388132!16s%2Fg%2F122cnw9p?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://tour.ulsan.go.kr/storyCms1/getImage.do?atchFileId=FILE_000000000002429&fileSn=0",
        name = "울산대교전망대",
        location = "울산 동구 봉수로 155-1",
        history = "울산에 있는 전망대",
        recentNews = "울산에 있는 엄청 높은 전망대",
        youTubeVideoId = null,
        address = "울산 동구 봉수로 155-1",
        openingHours = "09:00 ~ 21:00",
        googleMapUrl = "https://www.google.com/maps/place/Ulsan+Bridge+Observatory/data=!3m1!4b1!4m6!3m5!1s0x3567ce0720b091ed:0x1e94212be4f86d95!8m2!3d35.5017615!4d129.4061609!16s%2Fg%2F11bw51rgbv?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://mblogthumb-phinf.pstatic.net/MjAxODAxMjRfMjQ3/MDAxNTE2NzQ0NTM5Nzk1.KiMRpIKDnHqnuGuBKJsnCtl6eHXF6zus3YErkNUAVa8g.2k0j27gaHd4lUnCYQ1DQT339Dybvde5qeffrCUGwoTYg.JPEG.hyeogku/0103_%EC%9C%A0%EC%9B%90%EC%A7%80%EA%B3%B5%EC%A7%80%EC%B2%9C_041.JPG?type=w800",
        name = "공지천구름다리",
        location = "강원도 춘천시",
        history = "춘천에 있는 다리",
        recentNews = "춘천에 있는 엄청 귀여운 다리",
        youTubeVideoId = null,
        address = "강원 춘천시 근화동",
        openingHours = "24시",
        googleMapUrl = "https://www.google.com/maps/place/%EA%B5%AC%EB%A6%84%EB%8B%A4%EB%A6%AC/data=!4m10!1m2!2m1!1z6rO17KeA7LKc6rWs66aE64uk66as!3m6!1s0x3562e77e6156d38f:0x39f92520941b981!8m2!3d37.8710447!4d127.7156353!15sChXqs7Xsp4DsspzqtazrpoTri6TrpqySAQZicmlkZ2XgAQA!16s%2Fg%2F11t57hnk4p?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://image.여기유.com/content_travel/2020021409071915816388392284.jpg",
        name = "웅비탑",
        location = "충남 공주시",
        history = "충남 공주시에 있는 웅비탑",
        recentNews = "공주시에 있는 엄청 높은 웅비탑",
        youTubeVideoId = null,
        address = "충남 공주시 백제큰길 2104",
        openingHours = "24시",
        googleMapUrl = "https://www.google.com/maps/place/Ungbitab/data=!3m1!4b1!4m6!3m5!1s0x357ab833766e25d9:0x74aae0ce30a0e85e!8m2!3d36.4706148!4d127.1107369!16s%2Fg%2F119w9v5cx?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://cdn.todayscs.com/news/photo/202407/3085_5742_229.png",
        name = "촉석루",
        location = "경남 진주시",
        history = "경남 진주시에 있는 루 루 촉석루",
        recentNews = "진주시에 있는 엄청 긴 루 루 촉석루",
        youTubeVideoId = null,
        address = "경남 진주시 남강로 626",
        openingHours = "09:00 ~ 18:00",
        googleMapUrl = "https://www.google.com/maps/place/Chokseongnu+Pavilion/data=!3m1!4b1!4m6!3m5!1s0x356efc1488a4893f:0xc9987c0b1b26d1ef!8m2!3d35.1894393!4d128.0818561!16s%2Fm%2F0nbdy4c?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://file.newswire.co.kr/data/datafile2/thumb_480/2012/03/20120319103808_1086896175.jpg",
        name = "여수세계박람회스카이타워",
        location = "전남 여수시",
        history = "여수 엑스포 높은 스카이 타워",
        recentNews = "여수 엑스포에서 짱 높은 스카이 타워",
        youTubeVideoId = null,
        address = "전남 여수시 박람회길 1 국제관",
        openingHours = "임시 영업 중지",
        googleMapUrl = "https://www.google.com/maps/place/Sky+Tower+%26+Cafe+Sky/data=!3m1!4b1!4m6!3m5!1s0x356dd894012e3c6f:0xa8aff2df7f7f4ffa!8m2!3d34.7526659!4d127.7502918!16s%2Fg%2F11fk14wtg3?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0T4RbiiYifucgHFD54Y3hakvsON4msWmoiQ&s",
        name = "경주타워",
        location = "경상북도 경주시",
        history = "경상북도 경주시에 있는 타워",
        recentNews = "경북 경주시에 있는 엄청 높은 타워",
        youTubeVideoId = null,
        address = "경북 경주시 경감로 614",
        openingHours = "10:00 ~ 18:00",
        googleMapUrl = "https://www.google.com/maps/place/Gyeongju+Tower/data=!3m1!4b1!4m6!3m5!1s0x3566529a8b739911:0x728f663e41a21c5!8m2!3d35.8312589!4d129.2893624!16s%2Fg%2F122lkhpc?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://i.namu.wiki/i/AT4vvhHQkUJpy6447QoB8XD76LPpsSOw12YUNYSHDSDyNuJ6GoopxO51Q4nYr4dp3jxxTku1UbBbs2xkTZaC1Q.webp",
        name = "첨성대",
        location = "경상북도 경주시",
        history = "경상북도 경주시에 있는 첨성대",
        recentNews = "경북 경주시에 있는 엄청 높은 첨성대",
        youTubeVideoId = null,
        address = "경북 경주시 인왕동 839-1",
        openingHours = "9:00 ~ 22:00",
        googleMapUrl = "https://www.google.com/maps/place/Cheomseongdae+Observatory/data=!4m10!1m2!2m1!1z7LKo7ISx64yA!3m6!1s0x35664e67aead8a6b:0x28a9d45e5267e482!8m2!3d35.8346828!4d129.2190631!15sCgnssqjshLHrjICSAQ1oaXN0b3JpY19zaXRl4AEA!16zL20vMDI3MWQz?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://img.khan.co.kr/news/2022/03/24/l_2022032401003143200281521.jpg",
        name = "호미곶등대",
        location = "포항 남구",
        history = "포항 남구에 있는 호미곶등대",
        recentNews = "포항 남구에 있는 엄청 높은 호미곶등대",
        youTubeVideoId = null,
        address = "경북 포항시 남구 호미곶길 99",
        openingHours = "9:00 ~ 18:00",
        googleMapUrl = "https://www.google.com/maps/place/Homigot+Light+House/data=!3m1!4b1!4m6!3m5!1s0x3567116020656d93:0x25a1d6580e27ca60!8m2!3d36.077318!4d129.5691765!16s%2Fg%2F11dxdx907v?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://cdn.idomin.com/news/photo/202309/834137_509906_332.jpg",
        name = "양산타워",
        location = "경상남도 양산시",
        history = "경남 양산에 있는 높은 타워",
        recentNews = "경남 양산에 있는 짱 높은 타워",
        youTubeVideoId = null,
        address = "경남 양산시 강변로 264",
        openingHours = "10:00 ~ 23:00",
        googleMapUrl = "https://www.google.com/maps/place/%EC%96%91%EC%82%B0%ED%83%80%EC%9B%8C/data=!4m10!1m2!2m1!1z7JaR7IKw7YOA7JuM!3m6!1s0x356897a1fdb81e07:0x2b3c51e4b6155317!8m2!3d35.3260925!4d129.0240131!15sCgzslpHsgrDtg4Dsm4xaDyIN7JaR7IKwIO2DgOybjJIBEnRvdXJpc3RfYXR0cmFjdGlvbuABAA!16s%2Fg%2F11ksgp0rw?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://i.namu.wiki/i/uS37PZN_Dz11o6w2-_TM-rxg9NEbI1RY6Qio5DYWL0dEb-CWdiDUiuU4lPL0LF5ATUM13kJTISSFwyL4aCOXLg.webp",
        name = "광안대교",
        location = "부산시 수영구",
        history = "부산에 있는 짱 긴 다리",
        recentNews = "부산에 있는 짱 짱 긴 다리",
        youTubeVideoId = null,
        address = "부산시 수영구 민락동",
        openingHours = "24시",
        googleMapUrl = "https://www.google.com/maps/place/Gwangan+Bridge/data=!3m1!4b1!4m6!3m5!1s0x3568ed398bb5ba9b:0x6729727408f74eb0!8m2!3d35.147551!4d129.1302088!16zL20vMDh2OGNx?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    ),
    Landmark(
        images = "https://www.heritage.go.kr/unisearch/images/monument/thumb/2307932.jpg",
        name = "영도대교",
        location = "부산 중구",
        history = "부산 중구에 있는 영도대교",
        recentNews = "부산 중구에 있는 영영도도대대교교",
        youTubeVideoId = null,
        address = "부산 중구 중앙동7가",
        openingHours = "24시",
        googleMapUrl = "https://www.google.com/maps/place/Yeongdo+Bridge/data=!3m1!4b1!4m6!3m5!1s0x3568e9a5f055c17d:0xb8b4a18ee620a521!8m2!3d35.0956044!4d129.0364923!16s%2Fm%2F0nbdbx6?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
    )
)