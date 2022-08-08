package conflux.web3j.crypto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructuredDataTests {
    private static String jsonMessageString;

    @BeforeEach
    public void validSetUp() throws IOException, RuntimeException {
        String validStructuredDataJSONFilePath =
                "build/resources/test/" + "structured_data_json_files/ValidStructuredData.json";
        jsonMessageString = getResource(validStructuredDataJSONFilePath);
    }

    public String getResource(String jsonFile) throws IOException {
        return new String(
                Files.readAllBytes(Paths.get(jsonFile).toAbsolutePath()), StandardCharsets.UTF_8);
    }

    @Test
    public void testGetDependencies() throws IOException, RuntimeException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        Set<String> deps =
                dataEncoder.getDependencies(dataEncoder.jsonMessageObject.getPrimaryType());

        Set<String> depsExpected = new HashSet<>();
        depsExpected.add("Mail");
        depsExpected.add("Person");

        assertEquals(deps, depsExpected);
    }

    @Test
    public void testEncodeType() throws IOException, RuntimeException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        String expectedTypeEncoding =
                "Mail(Person from,Person to,string contents)"
                        + "Person(string name,address wallet)";

        assertEquals(
                dataEncoder.encodeType(dataEncoder.jsonMessageObject.getPrimaryType()),
                expectedTypeEncoding);
    }

    @Test
    public void testTypeHash() throws IOException, RuntimeException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        String expectedTypeHashHex =
                "0xa0cedeb2dc280ba39b857546d74f5549c" + "3a1d7bdc2dd96bf881f76108e23dac2";

        assertEquals(
                Numeric.toHexString(
                        dataEncoder.typeHash(dataEncoder.jsonMessageObject.getPrimaryType())),
                expectedTypeHashHex);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testEncodeData() throws RuntimeException, IOException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        byte[] encodedData =
                dataEncoder.encodeData(
                        dataEncoder.jsonMessageObject.getPrimaryType(),
                        (HashMap<String, Object>) dataEncoder.jsonMessageObject.getMessage());
        String expectedDataEncodingHex =
                "0xa0cedeb2dc280ba39b857546d74f5549c3a1d7bd"
                        + "c2dd96bf881f76108e23dac2fc71e5fa27ff56c350aa531bc129ebdf613b772b6"
                        + "604664f5d8dbe21b85eb0c8cd54f074a4af31b4411ff6a60c9719dbd559c221c8"
                        + "ac3492d9d872b041d703d1b5aadf3154a261abdd9086fc627b61efca26ae57027"
                        + "01d05cd2305f7c52a2fc8";

        assertEquals(Numeric.toHexString(encodedData), expectedDataEncodingHex);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testHashData() throws RuntimeException, IOException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        byte[] dataHash =
                dataEncoder.hashMessage(
                        dataEncoder.jsonMessageObject.getPrimaryType(),
                        (HashMap<String, Object>) dataEncoder.jsonMessageObject.getMessage());
        String expectedMessageStructHash =
                "0xc52c0ee5d84264471806290a3f2c4cecf" + "c5490626bf912d01f240d7a274b371e";

        assertEquals(Numeric.toHexString(dataHash), expectedMessageStructHash);
    }

    @Test
    public void testHashDomain() throws RuntimeException, IOException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        byte[] structHashDomain = dataEncoder.hashDomain();
        String expectedDomainStructHash =
                "0x08d4df1fd1a7d9c1a27a86b3b19b3258bd6f07d9ed1b88f52705f12453a4a5a1";

        assertEquals(Numeric.toHexString(structHashDomain), expectedDomainStructHash);
    }

    @Test
    public void testHashStructuredMessage() throws RuntimeException, IOException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        byte[] hashStructuredMessage = dataEncoder.hashStructuredData();
        String expectedDomainStructHash =
                "0xf930c72ca47e411d8671f3bee80e1d7594cd17a04355b15db5f11c2aba0a54e9";

        assertEquals(Numeric.toHexString(hashStructuredMessage), expectedDomainStructHash);
    }

    // CIP23
    @Test
    public void testValidTypedStructure() throws IOException {
        StructuredDataEncoder dataEncoder =
                new StructuredDataEncoder(
                        getResource(
                                "build/resources/test/"
                                        + "structured_data_json_files/ValidStructuredData.json"));

        assertEquals(
                "0xf930c72ca47e411d8671f3bee80e1d7594cd17a04355b15db5f11c2aba0a54e9",
                Numeric.toHexString(dataEncoder.hashStructuredData()));
    }

    @Test
    public void testGetArrayDimensionsFromData() throws RuntimeException, IOException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        // [[1, 2, 3], [4, 5, 6]]
        List<Object> testArrayData1 = new ArrayList<>();
        testArrayData1.add(new ArrayList<>(Arrays.asList("1", "2", "3")));
        testArrayData1.add(new ArrayList<>(Arrays.asList("4", "5", "6")));
        List<Integer> expectedDimensions1 =
                new ArrayList<Integer>() {
                    {
                        add(2);
                        add(3);
                    }
                };
        assertEquals(dataEncoder.getArrayDimensionsFromData(testArrayData1), expectedDimensions1);

        // [[1, 2, 3]]
        List<Object> testArrayData2 = new ArrayList<>();
        testArrayData2.add(new ArrayList<>(Arrays.asList("1", "2", "3")));
        List<Integer> expectedDimensions2 =
                new ArrayList<Integer>() {
                    {
                        add(1);
                        add(3);
                    }
                };
        assertEquals(dataEncoder.getArrayDimensionsFromData(testArrayData2), expectedDimensions2);

        // [1, 2, 3]
        List<Object> testArrayData3 =
                new ArrayList<Object>() {
                    {
                        add("1");
                        add("2");
                        add("3");
                    }
                };
        List<Integer> expectedDimensions3 =
                new ArrayList<Integer>() {
                    {
                        add(3);
                    }
                };
        assertEquals(dataEncoder.getArrayDimensionsFromData(testArrayData3), expectedDimensions3);

        // [[[1, 2], [3, 4], [5, 6]], [[7, 8], [9, 10], [11, 12]]]
        List<Object> testArrayData4 = new ArrayList<>();
        testArrayData4.add(
                new ArrayList<Object>() {
                    {
                        add(new ArrayList<>(Arrays.asList("1", "2")));
                        add(new ArrayList<>(Arrays.asList("3", "4")));
                        add(new ArrayList<>(Arrays.asList("5", "6")));
                    }
                });
        testArrayData4.add(
                new ArrayList<Object>() {
                    {
                        add(new ArrayList<>(Arrays.asList("7", "8")));
                        add(new ArrayList<>(Arrays.asList("9", "10")));
                        add(new ArrayList<>(Arrays.asList("11", "12")));
                    }
                });
        List<Integer> expectedDimensions4 =
                new ArrayList<Integer>() {
                    {
                        add(2);
                        add(3);
                        add(2);
                    }
                };
        assertEquals(dataEncoder.getArrayDimensionsFromData(testArrayData4), expectedDimensions4);
    }

    @Test
    public void testFlattenMultidimensionalArray() throws IOException, RuntimeException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessageString);
        // [[1, 2, 3], [4, 5, 6]]
        List<Object> testArrayData1 = new ArrayList<>();
        testArrayData1.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        testArrayData1.add(new ArrayList<>(Arrays.asList(4, 5, 6)));
        List<Integer> expectedFlatArray1 =
                new ArrayList<Integer>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                        add(4);
                        add(5);
                        add(6);
                    }
                };
        assertEquals(dataEncoder.flattenMultidimensionalArray(testArrayData1), expectedFlatArray1);

        // [[1, 2, 3]]
        List<Object> testArrayData2 = new ArrayList<>();
        testArrayData2.add(new ArrayList<>(Arrays.asList(1, 2, 3)));
        List<Integer> expectedFlatArray2 =
                new ArrayList<Integer>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                    }
                };
        assertEquals(dataEncoder.flattenMultidimensionalArray(testArrayData2), expectedFlatArray2);

        // [1, 2, 3]
        List<Object> testArrayData3 =
                new ArrayList<Object>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                    }
                };
        List<Integer> expectedFlatArray3 =
                new ArrayList<Integer>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                    }
                };
        assertEquals(dataEncoder.flattenMultidimensionalArray(testArrayData3), expectedFlatArray3);

        // [[[1, 2], [3, 4], [5, 6]], [[7, 8], [9, 10], [11, 12]]]
        List<Object> testArrayData4 = new ArrayList<>();
        testArrayData4.add(
                new ArrayList<Object>() {
                    {
                        add(new ArrayList<>(Arrays.asList(1, 2)));
                        add(new ArrayList<>(Arrays.asList(3, 4)));
                        add(new ArrayList<>(Arrays.asList(5, 6)));
                    }
                });
        testArrayData4.add(
                new ArrayList<Object>() {
                    {
                        add(new ArrayList<>(Arrays.asList(7, 8)));
                        add(new ArrayList<>(Arrays.asList(9, 10)));
                        add(new ArrayList<>(Arrays.asList(11, 12)));
                    }
                });
        List<Integer> expectedFlatArray4 =
                new ArrayList<Integer>() {
                    {
                        add(1);
                        add(2);
                        add(3);
                        add(4);
                        add(5);
                        add(6);
                        add(7);
                        add(8);
                        add(9);
                        add(10);
                        add(11);
                        add(12);
                    }
                };
        assertEquals(dataEncoder.flattenMultidimensionalArray(testArrayData4), expectedFlatArray4);
    }

}
