package com.tourney;

import com.webforj.App;
import com.webforj.annotation.AppProfile;
import com.webforj.annotation.AppTheme;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.Routify;
import com.webforj.annotation.StyleSheet;

@Routify(packages = "com.tourney.views")
@StyleSheet("ws://app.css")
@AppTitle("Tourney")
@AppTheme("system")
@AppProfile(name = "Tourney", shortName = "Tourney")
public class Application extends App {
}
