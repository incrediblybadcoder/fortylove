-- Add show_articles field to usersettings
ALTER TABLE fortylove.usersettings
    ADD show_articles boolean DEFAULT true;