package net.kwas.acore.util;

public class Icons {

    public static String getIconUrl(String rawReference) {
        if (rawReference == null) {
            return "";
        }

        var split = rawReference.split("\\\\");
        var iconName = split[split.length - 1];
        var lowerCase = iconName.toLowerCase();
        return "https://wow.zamimg.com/images/wow/icons/small/" + lowerCase + ".jpg";
    }

}
