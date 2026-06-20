public interface CsvHandler {
    String getCSVHeader();
    String toCSV();
    void fromCSV(String data);
}