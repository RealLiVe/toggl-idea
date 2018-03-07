package rip.faith_in_humanity.time.toggl;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import rip.faith_in_humanity.time.toggl.exception.TogglException;
import rip.faith_in_humanity.time.toggl.model.TogglSession;
import rip.faith_in_humanity.time.toggl.model.TogglTimeEntry;
import rip.faith_in_humanity.time.toggl.utils.Response;
import rip.faith_in_humanity.time.toggl.utils.SimpleIOTemplate;

import java.io.IOException;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class Toggl4J {



    private static final String TOGGL_ENDPOINT = "https://www.toggl.com/api/v8";

    private String apiToken;

    private int workspaceId;

    public static Toggl4J connect(String apiToken) throws TogglException {
        try (SimpleIOTemplate template = new SimpleIOTemplate(TOGGL_ENDPOINT + "/me",
                SimpleIOTemplate.RequestMethod.GET)) {
            template.addAuth(apiToken, "api_token");
            Response<TogglSession> object = template.send(TogglSession.class);
            if(object.getCode() != 200){
                return null;
            }
            int wid = object.getBody().getData().getDefault_wid();
            return new Toggl4J(apiToken, wid);
        } catch (IOException e) {
            return null;
        }
    }

//    public TogglTimeEntry activeEntry(long id) {
//
//    }
//
//    public TogglTimeEntry start(String description) {
//
//    }
//
//    public long create(TogglTimeEntry entry) {
//
//    }
//
//    public boolean stop(TogglTimeEntry entry) {
//
//    }

    private Toggl4J(String apiToken, int workspaceId) {
        this.apiToken = apiToken;
        this.workspaceId = workspaceId;
    }

    public static void main(String[] args) throws TogglException {
        Toggl4J toggl4J = connect("900a68a9edbcd857f739cdc176e6e34e");
        System.out.println();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
                    @Override
                    public void write(JsonWriter jsonWriter, ZonedDateTime zonedDateTime) throws IOException {
                        if(zonedDateTime != null){
                            jsonWriter.value(zonedDateTime.format(ISO_OFFSET_DATE_TIME));
                        }
                    }
                    @Override
                    public ZonedDateTime read(JsonReader jsonReader) throws IOException {
                        return ZonedDateTime.parse(jsonReader.nextString(), ISO_OFFSET_DATE_TIME);
                    }
                }).create();
        System.out.println(gson.toJson(new TogglTimeEntry(2, ZonedDateTime.now(), ZonedDateTime.now(), "Desc")));
    }
}
