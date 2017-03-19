package pl.pancor.android.air.data;


public class PollutionsContract {

    private PollutionsContract(){}

    public static abstract class Polluters {

        public static final String _ID = "_id";
        public static final String TABLE_NAME = "polluters";
        public static final String COLUMN_NAME_POLLUTER = "polluter";
        public static final String COLUMN_NAME_MAX_AQI = "max_aqi";
    }
}
