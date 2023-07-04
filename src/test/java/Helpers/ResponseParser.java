package Helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseParser {

    public static String getMessageFromJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Message message = objectMapper.readValue(jsonString, Message.class);

            String name = message.getMessage();
            System.out.println("Message: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
