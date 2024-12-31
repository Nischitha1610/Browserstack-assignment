import com.google.cloud.translate.*;

public class TranslationUtil {
    public static String translateToEnglish(String text) {
        // Using Google Cloud Translation API
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(
                text,
                Translate.TranslateOption.targetLanguage("en"),
                Translate.TranslateOption.sourceLanguage("es")
        );
        return translation.getTranslatedText();
    }
}
