package org.junit.experimental.theories.internal;

import com.itextpdf.text.pdf.PdfBoolean;
import java.util.Arrays;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

public class BooleanSupplier extends ParameterSupplier {
    public List<PotentialAssignment> getValueSources(ParameterSignature parameterSignature) {
        return Arrays.asList(new PotentialAssignment[]{PotentialAssignment.forValue(PdfBoolean.TRUE, true), PotentialAssignment.forValue(PdfBoolean.FALSE, false)});
    }
}
