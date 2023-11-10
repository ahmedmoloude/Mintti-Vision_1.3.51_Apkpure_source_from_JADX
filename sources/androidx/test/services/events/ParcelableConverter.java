package androidx.test.services.events;

import android.util.Log;
import androidx.test.services.events.internal.StackTrimmer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

public final class ParcelableConverter {
    private static final String TAG = "ParcelableConverter";

    private ParcelableConverter() {
    }

    public static TestCaseInfo getTestCaseFromDescription(Description description) throws TestEventException {
        List<AnnotationInfo> list;
        if (isValidJUnitDescription(description)) {
            List<AnnotationInfo> annotationsFromCollection = getAnnotationsFromCollection(description.getAnnotations());
            if (description.getTestClass() != null) {
                list = getAnnotationsFromArray(description.getTestClass().getAnnotations());
            } else {
                list = Collections.emptyList();
            }
            return new TestCaseInfo(description.getClassName(), description.getMethodName() != null ? description.getMethodName() : "", annotationsFromCollection, list);
        }
        String valueOf = String.valueOf(description);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 33);
        sb.append("Unexpected description instance: ");
        sb.append(valueOf);
        throw new TestEventException(sb.toString());
    }

    public static boolean isValidJUnitDescription(Description description) {
        return !description.equals(Description.EMPTY) && !description.equals(Description.TEST_MECHANISM);
    }

    public static List<AnnotationInfo> getAnnotationsFromArray(Annotation[] annotationArr) {
        ArrayList arrayList = new ArrayList(annotationArr.length);
        for (Annotation annotation : annotationArr) {
            arrayList.add(getAnnotation(annotation));
        }
        return arrayList;
    }

    public static List<AnnotationInfo> getAnnotationsFromCollection(Collection<Annotation> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (Annotation annotation : collection) {
            arrayList.add(getAnnotation(annotation));
        }
        return arrayList;
    }

    public static FailureInfo getFailure(Failure failure) throws TestEventException {
        return new FailureInfo(failure.getMessage(), failure.getTestHeader(), StackTrimmer.getTrimmedStackTrace(failure), getTestCaseFromDescription(failure.getDescription()));
    }

    public static List<FailureInfo> getFailuresFromList(List<Failure> list) throws TestEventException {
        ArrayList arrayList = new ArrayList();
        for (Failure failure : list) {
            arrayList.add(getFailure(failure));
        }
        return arrayList;
    }

    public static AnnotationInfo getAnnotation(Annotation annotation) {
        ArrayList arrayList = new ArrayList();
        for (Method annotationValue : annotation.annotationType().getDeclaredMethods()) {
            arrayList.add(getAnnotationValue(annotation, annotationValue));
        }
        return new AnnotationInfo(annotation.annotationType().getName(), (List<AnnotationValue>) arrayList);
    }

    private static AnnotationValue getAnnotationValue(Annotation annotation, Method method) {
        List list;
        String name = method.getName();
        String str = "NULL";
        try {
            Object invoke = method.invoke(annotation, (Object[]) null);
            str = getFieldValuesType(invoke);
            list = getArrayElements(invoke);
        } catch (Exception e) {
            String valueOf = String.valueOf(annotation);
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 48 + String.valueOf(valueOf).length());
            sb.append("Unable to get annotation values for field '");
            sb.append(name);
            sb.append("': [");
            sb.append(valueOf);
            sb.append("]");
            Log.e(TAG, sb.toString(), e);
            list = new ArrayList();
        }
        return new AnnotationValue(name, list, str);
    }

    private static String getFieldValuesType(Object obj) {
        return obj.getClass().getSimpleName().replace("[", "").replace("]", "");
    }

    static List<String> getArrayElements(Object obj) {
        ArrayList arrayList = new ArrayList();
        if (obj == null) {
            arrayList.add("<null>");
        } else if (obj.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(obj); i++) {
                arrayList.add(Array.get(obj, i).toString());
            }
        } else if (obj instanceof Iterable) {
            for (Object obj2 : (Iterable) obj) {
                arrayList.add(obj2.toString());
            }
        } else {
            arrayList.add(obj.toString());
        }
        return arrayList;
    }
}
