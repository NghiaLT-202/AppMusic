package com.example.appmusic.common

import java.util.LinkedList
import java.util.Random

object Constant {
    //Event toDetail
    const val PASS_DATA_FAVOURITE_MUSIC_TO_DETAIL = 1
    const val PASS_MUSIC_TO_DETAIL = 2
    const val KEY_SEARCH_HOME = "music trendding "
    const val KEY_SEARCH_HOME_LOAD_MORE = "Music"
    const val TYPE = "VIDEO"
    const val RUN_NEW_MUSIC = "RUN_NEW_MUSIC"
    const val EVENT_PASS_VIDEO = 0

    //Event
    const val CONNECT_S = 10
    const val READ_S = 10
    const val WRITE_S = 10

    //Event Sort
    const val SORT_NAME = "SORT_NAME"
    const val SORT_TIME = "SORT_TIME"

    //Service
    const val CHANGE_MUSIC_CURRENT = 11
    const val CHANGE_MUSIC_SERVICE = "CHANGE_MUSIC_SERVICE"
    const val COMPLETE_PLAY_MUSIC = 12
    const val START_MEDIA = 13
    const val START_MEDIA_SERVICE = "START_MEDIA_SERVICE"
    const val STOP_MEDIA_SERVICE = "STOP_MEDIA_SERVICE"
    const val STOP_MEDIA = 14
    const val FAVORITE_MEDIA = 15
    const val SEEK_TO_MEDIA_SERVICE = "SEEK_TO_MEDIA_SERVICE"
    const val PLAY_SONG = "PLAY_SONG"
    const val FAVOURITE = "FAVOURITE"
    const val NEXT_SONG = "NEXT_SONG"
    const val BACK_SONG = "BACK_SONG"

    //for DetailPlaylist
    const val NAME_PLAYLIST = "namePlaylist"

    //gg
    const val BASE_URL = "https://youtube.googleapis.com/youtube/v3/"
    const val PART_SNIPPET = "snippet"
    private var currentAPIKey = ""
    val aPIKey: String
        get() {
            val list: MutableList<String> = LinkedList()
            list.add("AIzaSyCoVRVeURj-q8336ILoqdUj4dukH5YxM-s")
            list.add("AIzaSyCzPc-a2NoQlr1r6-z79pZjHC8Oe7U4cU8")
            list.add("AIzaSyBMUjzN4CKSHtxuFKguTksxpK8MIyg3I2Y")
            list.add("AIzaSyCbNNRLk3Pvxw5ZbinDwL1PTeydWSlsEss")
            list.add("AIzaSyCyrI7vvvmy94ISqLelhA-WZvGlIkmeJCc")
            list.add("AIzaSyBYZBsu1ryIy6Yei4njML7w_pu9eGk69Tc")
            list.add("AIzaSyAxSma_MMh9WuQQULrmXscvSVQuvtthdMY")
            list.add("AIzaSyCrX6RDsVucuNNeewOIOgOxwd9UJg9ZaoU")
            list.add("AIzaSyCcB3eiJ9QTZtgV60_jeuaRn0K0HStMXWw")
            list.add("AIzaSyBerVzUR5s4OiFU-waj5noE3TGJKZFnPaY")
            list.add("AIzaSyBMUjzN4CKSHtxuFKguTksxpK8MIyg3I2Y")
            list.add("AIzaSyDudIA0e_-WBH5DTRQ3CenE2Yy9Frl3i-c")
            list.add("AIzaSyAL3lqms1QmvCiQS94c2GLK8CKabSraYdk")
            list.add("AIzaSyCE8_20a-82hKm7v6m3os-n13YNYNnV-ww")
            list.add("AIzaSyDEJN4Jj8HGnMxCTxJXQl42O-08L4wFK1c")
            list.add("AIzaSyDnU2HYeO3BXfPgT1X-XJMAmG9TCM_DctI")
            list.add("AIzaSyB7jreKtxZF99xfkBPZzsRpC3GAaOZj9JI")
            list.add("AIzaSyCRpoWaLcNOg84VtTS7P6pwjh9rGEN9wlA")
            list.add("AIzaSyDSg3rl8iaawdLwSB96hfWOnLPpD_r11tg")
            list.add("AIzaSyDYWXnr0z5TGmTuUVWDjOqbNjRk9OQJgrY")
            list.add("AIzaSyCAQUGyYNUnUXKTP_pBnM5WqmfbS_5mYHE")
            list.add("AIzaSyDe62-TqTciukZwWsHXQDm-YIGPrUNuWIo")
            list.add("AIzaSyDJ6oyRuP_H231UeY37bPpbvgxuxOkLj5c")
            list.add("AIzaSyD9hgl1VpIz_kUE9VMhkJS9CtpwE0Yyd78")
            list.add("AIzaSyB9QTQpGzrZgiwSrDqxxB-a0WoszJ5xUB0")
            list.add("AIzaSyC-Brq9nQ9H5R9fMTp_HF-5IYrMLkPvJvU")
            list.add("AIzaSyBZ2TebBYvZDu9TXFzw3WtFdW7_aTrZZdU")
            list.add("AIzaSyBBcDqb7QUFQisPEaGvlF0QGbRMapASIj8")
            list.add("AIzaSyDAsDHWh-K8feURBDslGdSLt-zSSzuwcNs")
            list.add("AIzaSyDkRLWGI8AlIhcOA42Cs5unuitkMjYizng")
            list.add("AIzaSyBHbPz0ayiC115NaDR9V6eb7mMx-qS5U2g")
            list.add("AIzaSyCzpnOgLq8wdYB6Scc5GZ34TrqCV-WHzwE")
            if (!currentAPIKey.isEmpty()) list.remove(currentAPIKey)
            val int_random = Random().nextInt(list.size)
            currentAPIKey = list[int_random]
            return currentAPIKey
        }
}