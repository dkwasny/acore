import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

import java.util.Objects;

public class TestRecordDbc {

    @DbcField(DbcType.UINT32)
    public long field1;

    @DbcField(DbcType.INT32)
    public int field2;

    @DbcField(DbcType.FLOAT)
    public float field3;

    @DbcField(DbcType.STRING)
    public String field4;

    public TestRecordDbc() {

    }

    public TestRecordDbc(long field1, int field2, float field3, String field4) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TestRecordDbc that = (TestRecordDbc) o;
        return field1 == that.field1 && field2 == that.field2 && Float.compare(field3, that.field3) == 0 && Objects.equals(field4, that.field4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field1, field2, field3, field4);
    }

    @Override
    public String toString() {
        return "TestDbcRecord{" +
            "field1=" + field1 +
            ", field2=" + field2 +
            ", field3=" + field3 +
            ", field4='" + field4 + '\'' +
            '}';
    }

}
