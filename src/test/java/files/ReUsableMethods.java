package files;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReUsableMethods {

	
	public static JsonPath rawToJson(String response)
	{
		JsonPath js1 =new JsonPath(response);
		return js1;
	}


	public static JsonPath rawToJson(Response resp) {

		JsonPath js1 =resp.jsonPath();
		return js1;
	}
}
