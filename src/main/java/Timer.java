import javafx.scene.Group;
import javafx.scene.text.Text;

public class Timer extends Group
{
    private Text[] digits = new Text[2];
    public Timer()
    {
        for (int i = 0; i < digits.length; i++) {
            digits[i] = new Text(String.valueOf(i));
        }
        getChildren().addAll(digits);
    }
}
