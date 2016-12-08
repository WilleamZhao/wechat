package com.sourcod.wechat.model;

public class JsonMsgRandCode extends JsonMsg {
    
    private Data data;
    
    public void setData(Data data) {
        this.data = data;
    }
    
    public Data getData() {
        return data;
    }
    
    @Override
    public String toString() {
        return "JsonMsg4Authcode [data=" + data + "]" + super.toString();
    }
    
    public class Data {
        
        private String result;
        
        private String msg;
        
        public void setResult(String result) {
            this.result = result;
        }
        
        public String getResult() {
            return result;
        }
        
        public void setMsg(String msg) {
            this.msg = msg;
        }
        
        public String getMsg() {
            return msg;
        }
        
        @Override
        public String toString() {
            return "Data [msg=" + msg + ", result=" + result + "]";
        }
        
    }
}
