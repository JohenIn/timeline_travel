package com.android.exampke.timeline_travel

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.parcelize.Parcelize

@Parcelize
data class Landmark(
    val images: String,
    val name: String,
    val location: String,
    val news: String? = null,
    val history: String,
    val recentNews: String,
    val youTubeVideoId: String?,
    val address: String,
    val openingHours: String,
    val googleMapUrl: String,
) : Parcelable

@Composable
fun getLandmarks(): List<Landmark> {
    return listOf(
        Landmark(
            images = "https://www.63realty.co.kr/upload/202007/0cf43966-cf30-420f-bba2-b4c871e4bc8a.jpg",
            name = stringResource(R.string.index0_name),
            location = stringResource(R.string.index0_location),
            news = stringResource(R.string.index0_news),
            history = "한 때 가장 높은",
            recentNews = "잘 모르겠음",
            youTubeVideoId = "https://youtu.be/HkoQCAMt6WI?si=B49wo4FCsxB8z7eB",
            address = "서울 영등포구 63로 50 한화금융센터_63",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/63+Building/data=!3m1!4b1!4m6!3m5!1s0x357c9f3c1822f59f:0x81e51bac4441f659!8m2!3d37.5198332!4d126.9400724!16zL20vMDZyYmd4?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://newsimg.sedaily.com/2018/06/22/1S0WPLM2WE_17.jpg",
            name = stringResource(R.string.index1_name),
            location = stringResource(R.string.index1_location),
            history = "멋진 장군님",
            recentNews = "잘 모르겠움,",
            youTubeVideoId = "https://youtu.be/bbMhpMgKScw?si=3ddN_EKcM0UdehWr",
            address = "서울 종로구 세종대로 172",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/Statue+of+Admiral+Yi+Sun-sin/data=!4m14!1m7!3m6!1s0x357ca2ec98800045:0xdd5786518f45a705!2sStatue+of+Admiral+Yi+Sun-sin!8m2!3d37.5710015!4d126.9769419!16s%2Fg%2F11g6rkhdqc!3m5!1s0x357ca2ec98800045:0xdd5786518f45a705!8m2!3d37.5710015!4d126.9769419!16s%2Fg%2F11g6rkhdqc?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://i.namu.wiki/i/DK-BcaE6wDCM-N9UJbeQTn0SD9eWgsX9YKWK827rqjbrzDz0-CxW-JFOCiAsUL3CBZ4zE0UDR-p4sLaYPiUjww.webp",
            name = stringResource(R.string.index2_name),
            location = stringResource(R.string.index2_location),
            news = stringResource(R.string.index2_news),
            history = "멋진 타워",
            recentNews = "잘 모르겠음 ytn",
            youTubeVideoId = "https://youtu.be/Dqk0nZMas9A?si=PSEVn1VbQohI6H6f",
            address = "서울 용산구 남산공원길 105",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/N+Seoul+Tower/data=!3m1!4b1!4m6!3m5!1s0x357ca257a88e6aa9:0x5cf8577c2e7982a5!8m2!3d37.5511694!4d126.9882266!16zL20vMDJxcGYx?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://newsroom.posco.com/kr/wp-content/uploads/2019/07/lotte.png",
            name = stringResource(R.string.index3_name),
            location = stringResource(R.string.index3_location),
            news = stringResource(R.string.index3_news),
            history = "엄청 높은 타워",
            recentNews = "롯데가 롯데월드 옆에 지은 짱 높은 타워",
            youTubeVideoId = "https://youtu.be/K_fVUCV4D4s?si=HqYuth2DIMj4_Y1D",
            address = "서울 송파구 올림픽로 300",
            openingHours = "09:00 ~ 20:30",
            googleMapUrl = "https://www.google.com/maps/place/LOTTE+WORLD+TOWER/data=!3m1!4b1!4m6!3m5!1s0x357ca50a915f665b:0xabeb10cd5efdfce2!8m2!3d37.5124641!4d127.102543!16s%2Fg%2F11by_z1kf2?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://i.namu.wiki/i/AUpk8QHygUJremQinbr7w6YT4BochQWlBBJ-UwY-WWjZ4_5l6-jjIkT7GT-Xd_EtMjzVdpus3HBemhyNlbEH4w.webp",
            name = stringResource(R.string.index4_name),
            location = stringResource(R.string.index4_location),
            news = stringResource(R.string.index4_news),
            history = "부산에 있는 타워",
            recentNews = "부산에 있는 엄청 높은 타워",
            youTubeVideoId = "https://youtu.be/FUm6D47Hg9U?si=NFBQ-LDGkVMQ5c74",
            address = "부산 중구 용두산길 37-30",
            openingHours = "10:00 ~ 22:00",
            googleMapUrl = "https://www.google.com/maps/place/Busan+Tower/data=!4m10!1m2!2m1!1z67aA7IKw7YOA7JuM!3m6!1s0x3568e974db8343ab:0xd25a159d0652ff4e!8m2!3d35.1011934!4d129.0323676!15sCgzrtoDsgrDtg4Dsm4xaDyIN67aA7IKwIO2DgOybjJIBEG9ic2VydmF0aW9uX2RlY2vgAQA!16s%2Fm%2F0fqrf7s?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://image.kmib.co.kr/online_image/2022/0318/2022031809301240453_1647563412_0016881139.jpg",
            name = stringResource(R.string.index5_name),
            location = stringResource(R.string.index5_location),
            news = stringResource(R.string.index5_news),
            history = "대구에 있는 타워",
            recentNews = "대구에 있는 엄청 높은 타워",
            youTubeVideoId = "https://youtu.be/z0rOH8DbtzY?si=jG6n1mPEgWffYa6y",
            address = "대구 달서구 두류공원로 200 83타워",
            openingHours = "11:00 ~ 20:30",
            googleMapUrl = "https://www.google.com/maps/place/Daegu+83+Tower/data=!3m1!4b1!4m6!3m5!1s0x3565e480574187d9:0xf93f6ce0fde7999!8m2!3d35.8533043!4d128.5665671!16s%2Fm%2F0gwzphh?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8PbRF4RzBw0Ckk0x5a0X2CQyQDjZlaMKypA&s",
            name = stringResource(R.string.index6_name),
            location = stringResource(R.string.index6_location),
            history = "인천에 있는 대교",
            recentNews = "인천에 있는 엄청 긴 대교",
            youTubeVideoId = "https://youtu.be/96KFfwFIJHk?si=Gz7KnBhbfm1p_Yvw",
            address = "인천 중구 인천대교고속도로 3 인천대교영업소및사무소",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/Incheon+Bridge/data=!3m1!4b1!4m6!3m5!1s0x357b9d019b658257:0xd26cd0e96d90acc2!8m2!3d37.4136893!4d126.5665206!16s%2Fm%2F02q9yrr?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://www.siminsori.com/news/photo/200701/55580-2-12570.jpg",
            name = stringResource(R.string.index7_name),
            location = stringResource(R.string.index7_location),
            history = "광주에 있는 상징탑",
            recentNews = "광주에 있는 엄청 높은 상징탑",
            youTubeVideoId = "https://youtu.be/lNmn3tkO2WQ?si=jpYLjoCZUD9aw4tr",
            address = "광주 북구 민주로 200 국립5.18민주묘지",
            openingHours = "09:00 ~ 18:00",
            googleMapUrl = "https://www.google.com/maps/place/May+18th+National+Cemetery/data=!3m1!4b1!4m6!3m5!1s0x357192c4573c905f:0xbc08e0b4883c5030!8m2!3d35.2353434!4d126.9401187!16s%2Fm%2F01270nh2?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRxfWpffTNtVTyNci9cC4AxZXOoODQOEor7uQ&s",
            name = stringResource(R.string.index8_name),
            location = stringResource(R.string.index8_location),
            history = "대전에 있는 한빛탑",
            recentNews = "대전에 있는 엄청 높은 한빛탑",
            youTubeVideoId = "https://youtu.be/6Imbvs9dFxQ?si=6LlocQ6Hw0SxlUZK",
            address = "대전 유성구 대덕대로 480",
            openingHours = "09:30 ~ 17:20",
            googleMapUrl = "https://www.google.com/maps/place/EXPO+Hanbit+Tower/data=!3m1!4b1!4m6!3m5!1s0x356549860756d7af:0x5bb388ce6c184d60!8m2!3d36.3765465!4d127.388132!16s%2Fg%2F122cnw9p?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://tour.ulsan.go.kr/storyCms1/getImage.do?atchFileId=FILE_000000000002429&fileSn=0",
            name = stringResource(R.string.index9_name),
            location = stringResource(R.string.index9_location),
            history = "울산에 있는 전망대",
            recentNews = "울산에 있는 엄청 높은 전망대",
            youTubeVideoId = "https://youtu.be/oxWsDZ2yZJk?si=7XiLdTjMJvQ8lYLk",
            address = "울산 동구 봉수로 155-1",
            openingHours = "09:00 ~ 21:00",
            googleMapUrl = "https://www.google.com/maps/place/Ulsan+Bridge+Observatory/data=!3m1!4b1!4m6!3m5!1s0x3567ce0720b091ed:0x1e94212be4f86d95!8m2!3d35.5017615!4d129.4061609!16s%2Fg%2F11bw51rgbv?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://mblogthumb-phinf.pstatic.net/MjAxODAxMjRfMjQ3/MDAxNTE2NzQ0NTM5Nzk1.KiMRpIKDnHqnuGuBKJsnCtl6eHXF6zus3YErkNUAVa8g.2k0j27gaHd4lUnCYQ1DQT339Dybvde5qeffrCUGwoTYg.JPEG.hyeogku/0103_%EC%9C%A0%EC%9B%90%EC%A7%80%EA%B3%B5%EC%A7%80%EC%B2%9C_041.JPG?type=w800",
            name = stringResource(R.string.index10_name),
            location = stringResource(R.string.index10_location),
            history = "춘천에 있는 다리",
            recentNews = "춘천에 있는 엄청 귀여운 다리",
            youTubeVideoId = "https://youtu.be/XQD0uG77dJM?si=V7PagNt1Nnvgev5y",
            address = "강원 춘천시 근화동",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/%EA%B5%AC%EB%A6%84%EB%8B%A4%EB%A6%AC/data=!4m10!1m2!2m1!1z6rO17KeA7LKc6rWs66aE64uk66as!3m6!1s0x3562e77e6156d38f:0x39f92520941b981!8m2!3d37.8710447!4d127.7156353!15sChXqs7Xsp4DsspzqtazrpoTri6TrpqySAQZicmlkZ2XgAQA!16s%2Fg%2F11t57hnk4p?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://image.여기유.com/content_travel/2020021409071915816388392284.jpg",
            name = stringResource(R.string.index11_name),
            location = stringResource(R.string.index11_location),
            history = "충청남도 공주시에 있는 웅비탑",
            recentNews = "공주시에 있는 엄청 높은 웅비탑",
            youTubeVideoId = null,
            address = "충남 공주시 백제큰길 2104",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/Ungbitab/data=!3m1!4b1!4m6!3m5!1s0x357ab833766e25d9:0x74aae0ce30a0e85e!8m2!3d36.4706148!4d127.1107369!16s%2Fg%2F119w9v5cx?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://cdn.todayscs.com/news/photo/202407/3085_5742_229.png",
            name = stringResource(R.string.index12_name),
            location = stringResource(R.string.index12_location),
            history = "경상남도 진주시에 있는 루 루 촉석루",
            recentNews = "진주시에 있는 엄청 긴 루 루 촉석루",
            youTubeVideoId = "https://youtu.be/HsXC7ufnvGg?si=nC08YDuEgEwFrpZJ",
            address = "경남 진주시 남강로 626",
            openingHours = "09:00 ~ 18:00",
            googleMapUrl = "https://www.google.com/maps/place/Chokseongnu+Pavilion/data=!3m1!4b1!4m6!3m5!1s0x356efc1488a4893f:0xc9987c0b1b26d1ef!8m2!3d35.1894393!4d128.0818561!16s%2Fm%2F0nbdy4c?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://file.newswire.co.kr/data/datafile2/thumb_480/2012/03/20120319103808_1086896175.jpg",
            name = stringResource(R.string.index13_name),
            location = stringResource(R.string.index13_location),
            history = "전라남도 여수 엑스포 높은 스카이 타워",
            recentNews = "여수 엑스포에서 짱 높은 스카이 타워",
            youTubeVideoId = "https://youtu.be/44ysCJ-otes?si=d-KOM-dqOMI5hI9U",
            address = "전남 여수시 박람회길 1 국제관",
            openingHours = "임시 영업 중지",
            googleMapUrl = "https://www.google.com/maps/place/Sky+Tower+%26+Cafe+Sky/data=!3m1!4b1!4m6!3m5!1s0x356dd894012e3c6f:0xa8aff2df7f7f4ffa!8m2!3d34.7526659!4d127.7502918!16s%2Fg%2F11fk14wtg3?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0T4RbiiYifucgHFD54Y3hakvsON4msWmoiQ&s",
            name = stringResource(R.string.index14_name),
            location = stringResource(R.string.index14_location),
            history = "경상북도 경주시에 있는 타워",
            recentNews = "경북 경주시에 있는 엄청 높은 타워",
            youTubeVideoId = null,
            address = "경북 경주시 경감로 614",
            openingHours = "10:00 ~ 18:00",
            googleMapUrl = "https://www.google.com/maps/place/Gyeongju+Tower/data=!3m1!4b1!4m6!3m5!1s0x3566529a8b739911:0x728f663e41a21c5!8m2!3d35.8312589!4d129.2893624!16s%2Fg%2F122lkhpc?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://i.namu.wiki/i/AT4vvhHQkUJpy6447QoB8XD76LPpsSOw12YUNYSHDSDyNuJ6GoopxO51Q4nYr4dp3jxxTku1UbBbs2xkTZaC1Q.webp",
            name = stringResource(R.string.index15_name),
            location = stringResource(R.string.index15_location),
            history = "경상북도 경주시에 있는 첨성대",
            recentNews = "경북 경주시에 있는 엄청 높은 첨성대",
            youTubeVideoId = "https://youtu.be/kcER5UNoqP0?si=geOWlpc-gl68TXzs",
            address = "경북 경주시 인왕동 839-1",
            openingHours = "9:00 ~ 22:00",
            googleMapUrl = "https://www.google.com/maps/place/Cheomseongdae+Observatory/data=!4m10!1m2!2m1!1z7LKo7ISx64yA!3m6!1s0x35664e67aead8a6b:0x28a9d45e5267e482!8m2!3d35.8346828!4d129.2190631!15sCgnssqjshLHrjICSAQ1oaXN0b3JpY19zaXRl4AEA!16zL20vMDI3MWQz?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://img.khan.co.kr/news/2022/03/24/l_2022032401003143200281521.jpg",
            name = stringResource(R.string.index16_name),
            location = stringResource(R.string.index16_location),
            history = "포항 남구에 있는 호미곶등대",
            recentNews = "포항 남구에 있는 엄청 높은 호미곶등대",
            youTubeVideoId = "https://youtu.be/vORdXrhwgBc?si=gBn_vs4a2xkyhcP6",
            address = "경북 포항시 남구 호미곶길 99",
            openingHours = "9:00 ~ 18:00",
            googleMapUrl = "https://www.google.com/maps/place/Homigot+Light+House/data=!3m1!4b1!4m6!3m5!1s0x3567116020656d93:0x25a1d6580e27ca60!8m2!3d36.077318!4d129.5691765!16s%2Fg%2F11dxdx907v?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://cdn.idomin.com/news/photo/202309/834137_509906_332.jpg",
            name = stringResource(R.string.index17_name),
            location = stringResource(R.string.index17_location),
            history = "경남 양산에 있는 높은 타워",
            recentNews = "경남 양산에 있는 짱 높은 타워",
            youTubeVideoId = "https://youtu.be/-qlaJuJXAOE?si=zGdPzVRkTok7tjGb",
            address = "경남 양산시 강변로 264",
            openingHours = "10:00 ~ 23:00",
            googleMapUrl = "https://www.google.com/maps/place/%EC%96%91%EC%82%B0%ED%83%80%EC%9B%8C/data=!4m10!1m2!2m1!1z7JaR7IKw7YOA7JuM!3m6!1s0x356897a1fdb81e07:0x2b3c51e4b6155317!8m2!3d35.3260925!4d129.0240131!15sCgzslpHsgrDtg4Dsm4xaDyIN7JaR7IKwIO2DgOybjJIBEnRvdXJpc3RfYXR0cmFjdGlvbuABAA!16s%2Fg%2F11ksgp0rw?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://i.namu.wiki/i/uS37PZN_Dz11o6w2-_TM-rxg9NEbI1RY6Qio5DYWL0dEb-CWdiDUiuU4lPL0LF5ATUM13kJTISSFwyL4aCOXLg.webp",
            name = stringResource(R.string.index18_name),
            location = stringResource(R.string.index18_location),
            history = "부산에 있는 짱 긴 다리",
            recentNews = "부산에 있는 짱 짱 긴 다리",
            youTubeVideoId = "https://youtu.be/gjVIRiIZSYU?si=P9mwOat1R7zdA1OZ",
            address = "부산시 수영구 민락동",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/Gwangan+Bridge/data=!3m1!4b1!4m6!3m5!1s0x3568ed398bb5ba9b:0x6729727408f74eb0!8m2!3d35.147551!4d129.1302088!16zL20vMDh2OGNx?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://www.heritage.go.kr/unisearch/images/monument/thumb/2307932.jpg",
            name = stringResource(R.string.index19_name),
            location = stringResource(R.string.index19_location),
            history = "부산 중구에 있는 영도대교",
            recentNews = "부산 중구에 있는 영영도도대대교교",
            youTubeVideoId = "https://youtu.be/4RRBIv9hvPE?si=IlvcP7XI_pod1eu0",
            address = "부산 중구 중앙동7가",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/Yeongdo+Bridge/data=!3m1!4b1!4m6!3m5!1s0x3568e9a5f055c17d:0xb8b4a18ee620a521!8m2!3d35.0956044!4d129.0364923!16s%2Fm%2F0nbdbx6?entry=ttu&g_ep=EgoyMDI1MDExNC4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://th.bing.com/th/id/OIP.UQCLibpWGUrWYQLbN4otuwAAAA?w=252&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7",
            name = stringResource(R.string.index20_name),
            location = stringResource(R.string.index20_location),
            history = "서울 종로구에 위치한 조선시대의 성문",
            recentNews = "흥인지문 보수 공사가 완료되었습니다.",
            youTubeVideoId = "https://youtu.be/example_heunginjimun",
            address = "서울 종로구 종로2가",
            openingHours = "09:00 - 18:00",
            googleMapUrl = "https://www.google.com/maps/place/%ED%9D%A5%EC%9D%B8%EC%A7%80%EB%AC%B8+(%EB%8F%99%EB%8C%80%EB%AC%B8)/data=!4m10!1m2!2m1!1z7Z2l7J247KeA66y4!3m6!1s0x357ca33b1d74ac9b:0x6027edc05f62ff6d!8m2!3d37.5710717!4d127.0096571!15sCgztnaXsnbjsp4DrrLhaDiIM7Z2l7J247KeA66y4kgETaGlzdG9yaWNhbF9sYW5kbWFya-ABAA!16zL20vMDNodmpy?entry=ttu&g_ep=EgoyMDI1MDExNS4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://th.bing.com/th/id/OIP.gI92eKawItLBVSCw2-XAuwAAAA?rs=1&pid=ImgDetMain",
            name = stringResource(R.string.index21_name),
            location = stringResource(R.string.index21_location),
            history = "조선 왕조의 대표적인 궁궐, 경복궁",
            recentNews = "경복궁 야간 특별 관람 프로그램이 진행 중입니다.",
            youTubeVideoId = "https://youtu.be/example_gyongbokgung",
            address = "서울 종로구 세종로",
            openingHours = "09:00 - 17:00",
            googleMapUrl = "https://www.google.com/maps/place/%EA%B2%BD%EB%B3%B5%EA%B6%81/data=!3m1!4b1!4m6!3m5!1s0x357ca2c74aeddea1:0x8b3046532cc715f6!8m2!3d37.579617!4d126.977041!16zL20vMDJ2M3Q2?entry=ttu&g_ep=EgoyMDI1MDExNS4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://th.bing.com/th/id/OIP.Peqxqphjs57TzqzSQJe_xQAAAA?w=210&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7",
            name = stringResource(R.string.index22_name),
            location = stringResource(R.string.index22_location),
            history = "창덕궁은 조선의 두 번째 궁궐로 아름다운 후원이 유명합니다.",
            recentNews = "창덕궁 후원 가이드 투어가 시작되었습니다.",
            youTubeVideoId = "https://youtu.be/example_changdeokgung",
            address = "서울 종로구 율곡로",
            openingHours = "09:00 - 18:00",
            googleMapUrl = "https://www.google.com/maps/place/%EC%B0%BD%EB%8D%95%EA%B6%81/data=!3m1!4b1!4m6!3m5!1s0x357ca25bce7408e3:0xff296b97da565068!8m2!3d37.5794309!4d126.9910426!16s%2Fg%2F11b7gr9kq4?entry=ttu&g_ep=EgoyMDI1MDExNS4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSXURfS_0aPDBeJfzlmtXnix2j7_xsI_KqHw&s",
            name = stringResource(R.string.index23_name),
            location = stringResource(R.string.index23_location),
            history = "창경궁은 왕과 왕비가 머물던 궁궐로 잘 알려져 있습니다.",
            recentNews = "창경궁 야간 산책 프로그램이 진행 중입니다.",
            youTubeVideoId = "https://youtu.be/example_changgyonggung",
            address = "서울 종로구 창경궁로",
            openingHours = "09:00 - 21:00",
            googleMapUrl = "https://www.google.com/maps/place/%EC%B0%BD%EA%B2%BD%EA%B6%81/data=!4m6!3m5!1s0x357ca2d63cdfd837:0x4ae06b4de851f3cf!8m2!3d37.5787512!4d126.9948775!16zL20vMDNuODI2?entry=ttu&g_ep=EgoyMDI1MDExNS4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8zW2Ubgb2kka22RT8ONVgdv0ZDAzjd5_GAg&s",
            name = stringResource(R.string.index24_name),
            location = stringResource(R.string.index24_location),
            history = "종묘는 조선 왕조의 왕과 왕비의 신위를 모신 사당입니다.",
            recentNews = "종묘 대제 행사가 곧 열릴 예정입니다.",
            youTubeVideoId = "https://youtu.be/example_jongmyo",
            address = "서울 종로구 종묘로",
            openingHours = "09:00 - 18:00",
            googleMapUrl = "https://www.google.com/maps/place/%EC%A2%85%EB%AC%98/data=!4m6!3m5!1s0x357ca2d85a1cbacf:0x24dfda0247e6e153!8m2!3d37.5746313!4d126.9938158!16s%2Fg%2F11b77jgfzf?entry=ttu&g_ep=EgoyMDI1MDExNS4wIKXMDSoASAFQAw%3D%3D"
        ),
        Landmark(
            images = "https://th.bing.com/th/id/OIP.wbFR6wWQN5N6TaSVD1fiyAAAAA?w=242&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7",
            name = stringResource(R.string.index25_name),
            location = stringResource(R.string.index25_location),
            history = "숭례문은 대한민국 국보 1호로 지정된 문화재입니다.",
            recentNews = "숭례문 주변 경관 정비가 완료되었습니다.",
            youTubeVideoId = "https://youtu.be/example_sungnyemun",
            address = "서울 중구 숭례문로",
            openingHours = "24시",
            googleMapUrl = "https://www.google.com/maps/place/%EC%88%AD%EB%A1%80%EB%AC%B8/data=!4m10!1m2!2m1!1z7Iit66GA66y4!3m6!1s0x357ca3cf536802a3:0xbd94666bbf6162e3!8m2!3d37.559984!4d126.9753071!15sCgnsiK3roYDrrLiSARNoaXN0b3JpY2FsX2xhbmRtYXJr4AEA!16zL20vMDJwemx3?entry=ttu&g_ep=EgoyMDI1MDExNS4wIKXMDSoASAFQAw%3D%3D"
        ),
    )
}
