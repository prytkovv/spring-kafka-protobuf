package ru.prytkovv.demo.conveter;


import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@Component
public class TimestampConverter {

    private final ZoneOffset offset;

    private TimestampConverter() {
        this.offset = ZoneOffset.UTC;
    }

    public Instant toInstant(Timestamp ts) {
        return Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
    }

    public OffsetDateTime toOffsetDateTime(Timestamp ts) {
        return OffsetDateTime.ofInstant(toInstant(ts), offset);
    }
}
