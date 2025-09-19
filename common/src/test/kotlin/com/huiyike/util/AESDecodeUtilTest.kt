package com.huiyike.util

import org.junit.jupiter.api.Test

class AESDecodeUtilTest {
    @Test
    fun `is code valid`() {
        val data = "tBfQflqGaE4qicv5H52vSwDN9VN-y6Iokacjdf7QMUmyfilSMsGK652oFtKi9PhS"
        val decodeData = AESDecodeUtil.aesDecryptECB(data)
        println("decodeData = $decodeData")
    }

    @Test
    fun `is code valid with empty data`() {
        val data = "{\"username\":\"15942476056\",\"password\":\"123456\"}"
        val decodeData = AESDecodeUtil.aesEncryptECB(data)
        println("encrypted codeData = $decodeData")
    }

    @Test
    fun `test generate social data`() {
        val socialString = "{\"socialId\":null,\"logo\":null,\"fullName\":\"杭州篮球协会\",\"abbrName\":\"杭篮协\"," +
                "\"introduction\":\"杭州篮球协会是一个非营利性社会组织，致力于推动篮球运动的发展，组织各类篮球赛事和活动，促进篮球文化的传播。\"" +
                ",\"industry\":\"体育\",\"creator\":\"admin\",\"createTime\":1696118400000,\"updateTime\":null,\"status\":1," +
                "\"remark\":null,\"category\":\"协会\"}"
        val encryptedData = AESDecodeUtil.aesEncryptECB(socialString)
        println("encryptedData = $encryptedData")
    }

    @Test
    fun `test generate event data based on Event pojo`() {
        val eventStr = "{\"eventId\":null,\"banner\":null,\"fullName\":\"2023年杭州市篮球联赛\"," +
                "\"abbrName\":\"2023杭篮联赛\",\"introduction\":\"2023年杭州市篮球联赛是由杭州篮球协会主办的年度篮球赛事，旨在促进本地篮球运动的发展，提升球员的竞技水平，增强社区凝聚力。\"" +
                ",\"detail\":\"赛事将于2023年10月1日正式开赛，持续至2023年12月31日。比赛地点设在杭州市各大体育馆，预计吸引来自全市各地的20支球队参赛。联赛采用小组赛加淘汰赛的形式进行，每场比赛均有专业裁判执法，确保比赛的公平公正。\"" +
                ",\"category\":\"赛事\",\"industry\":\"体育\",\"city\":\"杭州\",\"country\":\"中国\",\"courtName\":\"杭州奥体中心\"," +
                "\"courtLat\":\"30.244\",\"courtLng\":\"120.206\",\"detailAddr\":\"浙江省杭州市奥体中心路1号\"," +
                "\"square\":\"4200平方米\",\"startingDate\":1701427200,\"endDate\":1704067200,\"createTime\":1696118400000," +
                "\"updateTime\":null,\"creator\":\"admin\",\"status\":1,\"remark\":null,\"socialId\":null}"
        val encryptedData = AESDecodeUtil.aesEncryptECB(eventStr)
        println("encryptedData = $encryptedData")
    }
}