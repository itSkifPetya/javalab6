package common.domain.command;

import java.io.*;

public class Serializer {
    private static Serializer instance;
    private Serializer() {}

    public static Serializer getInstance() {
        if (instance == null) instance = new Serializer();
        return instance;
    }

    public byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        return bos.toByteArray();
    }

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    public Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(is);
        return ois.readObject();
    }

    public void serialize(Object object, OutputStream os) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(object);
        oos.flush();
    }
/*

    public byte[] serialize(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(o);
        oos.flush();
        return bos.toByteArray();
    }

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
*/



}
