import discord
import os
from discord.ext import commands
from discord_slash import SlashCommand, SlashContext

class discordClient(discord.Client):
    __is_online = False
    bot = commands.Bot(command_prefix="!", intents=discord.Intents.all())
    __slash = SlashCommand(bot)
    
    async def on_ready(self):
        __is_online = True
        print("Connection established")
    
    @__slash.slash(name="test")
    async def _test(ctx: SlashContext):
        embed = discord.Embed(title="test")
        await ctx.send(content="/commands worked", embeds=[embed])

  

discordClient.bot.run(os.getenv('BITOWL_TOKEN'))