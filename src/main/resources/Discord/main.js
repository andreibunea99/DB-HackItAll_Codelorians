const Discord = require('discord.js');
const fs = require('fs');

const client = new Discord.Client();

client.once('ready', () => {
    console.log('I\'m online');
});

const prefix = '-';

client.on('message', message => {
    if (!message.content.startsWith(prefix) || message.author.bot) {
        return;
    }

    const args = message.content.slice(prefix.length).split(/ +/);
    const command = args.shift().toLowerCase();

    if (command === 'sex') {
        message.channel.send('sex!');
    }
});

fs.readFile('/etc/hosts', 'utf8', function (err,data) {
    if (err) {
      return console.log(err);
    }
    console.log(data);
  });

client.login('Nzg0NzExMjg0MzE3ODgwMzIw.X8tRVQ.ivnVrrtSMEyeUNhgogx4yDhK5VY');
