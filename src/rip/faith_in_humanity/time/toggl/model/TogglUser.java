package rip.faith_in_humanity.time.toggl.model;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import rip.faith_in_humanity.time.toggl.utils.IOUtils;
import rip.faith_in_humanity.time.toggl.utils.Response;
import rip.faith_in_humanity.time.toggl.utils.SimpleIOTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class TogglUser {

    private int default_wid;

    private String api_token;

    public int getDefault_wid() {
        return default_wid;
    }

    public void setDefault_wid(int default_wid) {
        this.default_wid = default_wid;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public static void main(String[] args) throws Exception {
//        JSONObject request = new JSONObject();
//        JSONObject data = new JSONObject();
//        data.put("description", "API TEST");
//        data.put("duration", 1200);
//        data.put("start", ZonedDateTime.now().format(ISO_OFFSET_DATE_TIME));
//        data.put("wid", 2061613);
//        data.put("created_with", "IDEA");
//        request.put("time_entry", data);
//        System.out.println(request.toString());
//
//        JSONObject time = new JSONObject("{\n" +
//                "\t\"data\":{\n" +
//                "\t\t\"id\":436694100,\n" +
//                "\t\t\"wid\":777,\n" +
//                "\t\t\"pid\":193791,\n" +
//                "\t\t\"tid\":13350500,\n" +
//                "\t\t\"billable\":false,\n" +
//                "\t\t\"start\":\"2013-02-27T01:24:00+00:00\",\n" +
//                "\t\t\"stop\":\"2013-02-27T07:24:00+00:00\",\n" +
//                "\t\t\"duration\":21600,\n" +
//                "\t\t\"description\":\"Some serious work\",\n" +
//                "\t\t\"tags\":[\"billed\"],\n" +
//                "\t\t\"at\":\"2013-02-27T13:49:18+00:00\"\n" +
//                "\t}\n" +
//                "}");

        TogglTimeEntry togglTimeEntry = new TogglTimeEntry(2061613, "Test");
        try (SimpleIOTemplate template = new SimpleIOTemplate("https://www.toggl.com/api/v8/time_entries", SimpleIOTemplate.RequestMethod.POST)) {
            template.addAuth("900a68a9edbcd857f739cdc176e6e34e", "api_token");
            Response<Map> test = template.send(togglTimeEntry, Map.class);
            System.out.println();
        }



//        try (SimpleIOTemplate template = new SimpleIOTemplate("https://www.toggl.com/api/v8/time_entries", SimpleIOTemplate.RequestMethod.POST)) {
//            template.addAuth("900a68a9edbcd857f739cdc176e6e34e", "api_token");
//            JSONObject object = template.fire(request);
//            System.out.println();
//        }
//         HttpURLConnection urlConnection = (HttpURLConnection) new URL("https://www.toggl.com/api/v8/me").openConnection();
//         String credentials = "900a68a9edbcd857f739cdc176e6e34e:api_token";
//         urlConnection.setRequestMethod("GET");
//         urlConnection.setRequestProperty("Authorization", "Basic "+Base64.getEncoder().encodeToString(credentials.getBytes()));
//        urlConnection.setDoInput(true);
//        urlConnection.setDoOutput(true);
//        String message = urlConnection.getResponseMessage();
//        if(200 <= urlConnection.getResponseCode() && urlConnection.getResponseCode() <= 299 ){
//            message = IOUtils.toString(urlConnection.getInputStream());
//            System.out.println(message);
//        }
//
//        System.out.println();
    }


}
