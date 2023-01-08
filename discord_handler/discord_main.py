import os
import disnake
from disnake.ext import commands

class discordClient(disnake.Client):
    __is_online = False
    cmd_sync_flag = commands.CommandSyncFlags.default()
    bot = commands.Bot(command_prefix="!", command_sync_flags=cmd_sync_flag)
    inter = disnake.ApplicationCommandInteraction()

    async def on_ready(self):
        __is_online = True
        print("Connection established")
    
    @bot.slash_command(description="This is a test command. It should respond with 'Test command successful.'")
    async def test(inter):
        await inter.response.send("Test command worked")

  
client = discordClient()
client.bot.run(os.getenv('BITOWL_TOKEN'))