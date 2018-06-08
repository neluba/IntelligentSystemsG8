package de.competition.thesis.helper;

import com.google.gson.Gson;


/**
 * Kann verwendet werden, um Objekte zu Kopieren. löst sämtliche referenz probleme
 */
public class Cloner {

        public static String serializeObject(Object o) {
            Gson gson = new Gson();
            String serializedObject = gson.toJson(o);
            return serializedObject;
        }

        public static Object unserializeObject(String s, Object o){
            Gson gson = new Gson();
            Object object = gson.fromJson(s, o.getClass());
            return object;
        }
        public static Object cloneObject(Object o){
            String s = serializeObject(o);
            Object object = unserializeObject(s,o);
            return object;
        }

}
