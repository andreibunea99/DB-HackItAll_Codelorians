const Discord = require('discord.js');
const fs = require('fs');
const { on } = require('process');

const client = new Discord.Client();

var canal;

client.once('ready', () => {
    console.log('I\'m online');
});

console.log(client.guilds);

const prefix = '-';

client.on('message', message => {
    if (!message.content.startsWith(prefix) || message.author.bot) {
        return;
    }

    const args = message.content.slice(prefix.length).split(/ +/);
    const command = args.shift().toLowerCase();

    if (command === 'salut') {
        message.channel.send('sal!');
    }
});

client.on('message', message => {
    if (!message.content.startsWith(prefix) || message.author.bot) {
        return;
    }

    const args = message.content.slice(prefix.length).split(/ +/);
    const command = args.shift().toLowerCase();

    if (command === 'read') {
        fs.readFile('command.txt', 'utf8', function (err,data) {
            if (err) {
              return console.log(err);
            }
            message.channel.send(data);
          });
    }
});


client.login('Nzg0NzExMjg0MzE3ODgwMzIw.X8tRVQ.ivnVrrtSMEyeUNhgogx4yDhK5VY');
