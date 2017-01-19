package cn.zhanyongzhi.study.protobuf;

import cn.zhanyongzhi.study.example.AddressBookProtos;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class CreateInstanceTest {
    @Test
    public void testDefault(){
        AddressBookProtos.Person.Builder personBuilder = AddressBookProtos.Person.newBuilder();
        AddressBookProtos.Person person = personBuilder
                .setEmail("test@email")
                .setId(1)
                .setName("hello")
                .build();

        System.out.println(person.getEmail());
    }

    @Test
    public void testWriteFile() throws IOException, URISyntaxException {
        URI resourceURI = getClass().getClassLoader().getResource("").toURI();

        File serializeWriteFile = Paths.get(new File(resourceURI).getAbsolutePath(), "ProtoSerializeFile").toFile();
        FileOutputStream fileOutputStream = new FileOutputStream(serializeWriteFile);

        AddressBookProtos.Person.Builder personBuilder = AddressBookProtos.Person.newBuilder();
        AddressBookProtos.Person person = personBuilder
                .setEmail("test@email")
                .setId(1)
                .setName("hello")
                .build();

        person.writeTo(fileOutputStream);
    }

    @Test
    public void testParseFile() throws IOException, URISyntaxException {
        testWriteFile();

        URI resourceURI = getClass().getClassLoader().getResource("").toURI();

        File serializeWriteFile = Paths.get(new File(resourceURI).getAbsolutePath(), "ProtoSerializeFile").toFile();
        FileInputStream fileInputStream = new FileInputStream(serializeWriteFile);

        AddressBookProtos.Person person = AddressBookProtos.Person.parseFrom(fileInputStream);

        System.out.println(person.getEmail());
    }
}
