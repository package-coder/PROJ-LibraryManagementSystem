package sample.classes;

import java.io.*;

public class ObjectStream {
    public static byte[] objectToByteArray(Object object){

        try(var bos = new ByteArrayOutputStream();
            var oos = new ObjectOutputStream(bos)){

            oos.writeObject(object);

            return bos.toByteArray();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object byteArrayToObject(byte[] bytes){

        try(var bos = new ByteArrayInputStream(bytes);
            var oos = new ObjectInputStream(bos)){

            return oos.readObject();

        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
