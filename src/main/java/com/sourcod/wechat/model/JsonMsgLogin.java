package com.sourcod.wechat.model;


public class JsonMsgLogin extends JsonMsg {
    
	private JsonMsgData data;
    
    public void setData(JsonMsgData data) {
        this.data = data;
    }
    
    public JsonMsgData getData() {
        return data;
    }
    

    
    @Override
    public String toString() {
        return "JsonMsg4Login []" + super.toString();
    }


}
