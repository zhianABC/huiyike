package com.huiyike.response

class HuiyikeResponse {
    var code: Int = 0
    var msg: String = ""
    var data: Any = ""
    var err: String? = null

    constructor(code: Int, msg: String, data: Any) {
        this.code = code
        this.msg = msg
        this.data = data
    }

    constructor(code: Int, msg: String, data: Any, err: String?) {
        this.code = code
        this.msg = msg
        this.data = data
        this.err = err
    }
}