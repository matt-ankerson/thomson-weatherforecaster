package bit.ankem1.WeatherWorks;

/**
 * Created by matt on 15/05/15.
 */
// This class serves as a type for the custom ArrayAdapter
public class Provider
{
    private String name;
    private int imageResource;

    public  Provider(String name, int imageResource)
    {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName()
    {
        return name;
    }
    public int getImageResource()
    {
        return imageResource;
    }
}
