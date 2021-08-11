from discord_handler.discord_main import quotes
import os

quotes.get_bot.run(os.getenv('BITOWL_TOKEN'))

