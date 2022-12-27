from discord_handler.discord_main import quotes
from multiprocessing import Process
import os

BOT_TOKEN: str = ""

# Show the control panel GUI for bot.
CTRL_PN_GUI: str = FALSE

# Display splash screen if applicable
if CTRL_PN_GUI is TRUE:
    #splash_scr.show()


#Get Discord bot token and run Discord Bot.
try:
    BOT_TOKEN = os.getenv('BITOWL_TOKEN')
except FileError as fe:
    # BOT_TOKEN = get_tok_inp()
finally:
    if CTRL_PN_GUI is true:
        #splash_scr.close()
        gui_proc = #Process(target=gui.start, args=(,))
    core_proc = #Process(target=quotes.get_bot.run, args=(BOT_TOKEN,)
    core_proc.start()
    if CTRL_PN_GUI:
        gui_proc.start()
        gui_proc.join()
    core_proc.start()
