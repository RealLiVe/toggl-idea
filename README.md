# Note
This plug-in is currently neither functional nor maintained. 

# toggl-idea
This plug-in for IntelliJ IDEA integrates the Toggl Time Tracker in the IDE in a non-annoying way. 

### Motivation
It is annoying to track the exact amount of time you spend on an specific issue in Toggl. You would have to switch the application, stop the old entry, start a new one an specify both project and description. That's annoying and i forgot to do that more than once, if i had to switch the project for a while. 

So i developed this small plug-in. It adds a button to the toolbar and makes use of the project and the currently selected task. The built-in functionality of intellij is able to pull the issues from common issue trackers (GitHub, Mantis, JIRA).

After you push the button, you can let the plug-in do the rest. If you switch the project, then the time tracker will do that aswell. It you change the task in your IDE, then the time tracker will also notice that. And even if you don't care about the tasks, it is comfortable to start and stop the tracking in your IDE and let the plug-in set the project automatically. 

### Usage
- Download the plugin jar (github release). 
- In IDEA, go to File > Settings > Plug-Ins.
- Install plugin from disk...
- restart IDE.
- go to File > Settings > Other Settings > Toggl Time Tracker
- Enter your API Key. You find this key in your toggl profile settings ( https://toggl.com/app/profile )
- Apply. 

### Support
Suggestions, problems and bugs should be placed in the issue tracker. 

### Recommendations
- install the plugin wide-task-browser. It displays the comments in a better way and may be more usable than the integrated solution. 

### Limitations
- Start and Stop are manual actions. Time Tracker will not be stopped, if the IDE get's closed. 
- Idle detection is not built-in. However, other existing solutions can provide that. It should be no problem if you use the Browser extension provided by Toggl and the Plug-In. 
- It only uses the default workspace. 
- There is no view in IntelliJ which shows the tracked time. 
- You can simultaneously open multiple projects. However, you should not run intellij with this extension on different machines.  

### Licence
MIT. 
