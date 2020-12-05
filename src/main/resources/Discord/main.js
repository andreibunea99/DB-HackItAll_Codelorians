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


    if (command === 'move') {
        // var member = message.author;

        // var cosmins = client.channels.cache.array().join(', ');

        // console.log(member);
        client.guilds.fetch('784718151219937290').then((guild) => {
            var members = guild.members;
            members.fetch('203203790017527808').then((user) => {
                // user.edit("channel_id", "784720436473888789");
                user.edit({channel_id:"784720436473888789"});
            })
        })
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
