#TODO: Implement methods that are commented out.

from discord_handler.discord_main import quotes
from multiprocessing import Process
import os
import sys

BOT_TOKEN: str = ""

# Show the control panel GUI for bot.
LAUNCH_CTRL_PN_GUI: str = False

# Display splash screen if applicable
if LAUNCH_CTRL_PN_GUI is True:
    # Check if another instance of GUI is running and
    # warn user of this if applicable.
    # user can launch not without GUI, force launch GUI,
    # or shutdown bot
    # If PID = -1, no process is running.
    existing_gui_pid: int = gui_proc_active()
    force_launch_gui: bool = False
    if (existing_gui_pid == -1):
        #splash_scr.show()
    elif (existing_gui_pid > -1):
        force_launch_gui = #warn_dup_gui(existing_gui_pid)
            if force_launch_gui == -1:
                sys.exit(0)
            elif:
                #splash_scr.show()


#Get Discord bot token and run Discord Bot.
#Then launch main GUI and core processes.
# Token input will be prompted to user if not found.
try:
    BOT_TOKEN = os.getenv('BITOWL_TOKEN')
except FileError as fe:
    # BOT_TOKEN = get_tok_inp(using_GUI=LAUNCH_CTRL_PN_GUI)
finally:
    if LAUNCH_CTRL_PN_GUI is true:
        #splash_scr.close()
        gui_proc = #Process(target=gui.start, args=(,))
    core_proc = #Process(target=quotes.get_bot.run, args=(BOT_TOKEN,))
    # Get ID of existing core process, or get -1 if no core
    # process is running
    existing_core_pid = core_proc_active()
    # if (existing_core_pid == -1):
        core_proc.start()
    #elif (existing_core_pid > -1):
        if LAUNCH_CTRL_PN_GUI is FALSE:
            # function definition
            # DupCoreProcException(core_pid, expect_GUI)
            # raise DupCoreProcException(existing_core_pid,False)
        else:
            #Connect GUI to existing core process
            gui_proc = #Process(target=gui.start, args=(existing_core_pid,))
    if CTRL_PN_GUI:
        gui_proc.start()
        gui_proc.join()
    
    core_proc.join()
