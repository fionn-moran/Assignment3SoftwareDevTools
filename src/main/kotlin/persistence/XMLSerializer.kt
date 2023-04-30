package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import com.thoughtworks.xstream.security.AnyTypePermission
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import kotlin.Throws

class XMLSerializer(private val file: File) : Serializer {

    // Tests were failing on security issues re: the coffeeShop model
    // so I added xStream.addPermission(AnyTypePermission.ANY),
    // reference for this solution: https://stackoverflow.com/questions/30812293/com-thoughtworks-xstream-security-forbiddenclassexception
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.addPermission(AnyTypePermission.ANY)
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        xStream.addPermission(AnyTypePermission.ANY)
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
