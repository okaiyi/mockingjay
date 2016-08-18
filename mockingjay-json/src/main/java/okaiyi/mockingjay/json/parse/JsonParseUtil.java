package okaiyi.mockingjay.json.parse;


import okaiyi.mockingjay.commons.data.DataTyper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * JSON数据解析
 */
public  class JsonParseUtil {
    //存放原始返回的json数据
    public static final String KEY_SRC_JSON="src_json";
    
    /*
     * 解析复杂的JSON到ArrayMap中
     */
    public static final Map<String, DataTyper> parser(String jsonString) throws JSONException {
        JSONObject jo = new JSONObject(jsonString);
        Map<String, DataTyper> am = new HashMap<String,DataTyper>();
        Iterator<String> keys = jo.keys();
        for (; keys.hasNext();) {
            String key = keys.next();
            Object obj = jo.get(key);
            if (obj instanceof JSONArray) {
                am.put(key, new DataTyper(parse((JSONArray) obj)));
            } else {
                am.put(key, new DataTyper(obj));
            }
        }
        am.put(KEY_SRC_JSON, new DataTyper(jsonString));
        return am;
    }
    private static final List<Map<String, DataTyper>> parse(JSONArray jr)
            throws JSONException {
        List<Map<String, DataTyper>> arrayList = new ArrayList<Map<String, DataTyper>>();
        for (int i = 0; i < jr.length(); i++) {
            JSONObject jo = (JSONObject) jr.get(i);
            Iterator<String> keys = jo.keys();
            Map<String, DataTyper> map = new HashMap<String, DataTyper>();
            for (; keys.hasNext();) {
                String key = keys.next();
                Object obj = jo.get(key);
                if (obj instanceof JSONArray) {
                    map.put(key, new DataTyper(parse((JSONArray) obj)));
                } else {
                    map.put(key, new DataTyper(obj.toString()));
                }
            }
            arrayList.add(map);
        }
        return arrayList;
    }
}
