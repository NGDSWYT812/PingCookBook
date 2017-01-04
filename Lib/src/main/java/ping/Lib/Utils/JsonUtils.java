package ping.Lib.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	public static String getString(JSONObject obj, String filed) throws JSONException {
		return (obj.has(filed) ? obj.getString(filed) : null);
	}

	public static int getInt(JSONObject obj, String filed) throws JSONException {
		return (obj.has(filed) ? obj.getInt(filed) : 0);
	}

	public static double getDouble(JSONObject obj, String filed) throws JSONException {
		return (obj.has(filed) ? obj.getDouble(filed) : 0);
	}

	public static long getLong(JSONObject obj, String filed) throws JSONException {
		return (obj.has(filed) ? obj.getLong(filed) : 0);
	}

	public static boolean getBoolean(JSONObject obj, String filed) throws JSONException {
		return (obj.has(filed) ? obj.getBoolean(filed) : false);
	}

	public static JSONObject getJSONObject(JSONObject obj, String filed) throws JSONException {
		return (obj.has(filed) ? new JSONObject(obj.getString(filed)) : new JSONObject());
	}

	public static JSONArray getJSONArray(JSONObject obj, String filed) throws JSONException {
		return (obj.has(filed) ? new JSONArray(obj.getString(filed)) : new JSONArray());
	}
}
