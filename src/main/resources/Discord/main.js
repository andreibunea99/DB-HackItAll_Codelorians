var http = require('http');
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
    if (!message.content.startsWith(prefix)) {
        return;
    }

    const args = message.content.slice(prefix.length).split(/ +/);
    const command = args.shift().toLowerCase();

    if (command === 'salut') {
        message.channel.send('sal!');
    }

    if (command === 'move') {

        var userName = args[0];
        var channelName = args[1];

        client.guilds.fetch('784718151219937290').then((guild) => {
            var members = guild.members;
            for (const [key, value] of members.cache) {
                if (value.nickname === userName) {
                    members.fetch(value.id).then((user) => {
                        user.edit({channel_id:channelName});
                    })
                }
            }
        })
    }
});

client.on('voiceStateUpdate', (oldMember, newMember) => {
    let newUserChannel = newMember.channelID;
    let oldUserChannel = oldMember.channelID;

    var userID = newMember.member.id;

    if (newUserChannel !== oldUserChannel) {
        var nick;
        oldMember.guild.members.fetch(userID).then((user) => {
            nick = user.nickname;

            var request = http.request({
                port: 8080,
                host: '127.0.0.1',
                method: 'GET',
                path: "/get-rooms-info?name=" + nick + "&newChannel=" + newUserChannel + "&oldChannel=" + oldUserChannel
            })
            request.end();
        })
    }
})


client.login('Nzg0NzExMjg0MzE3ODgwMzIw.X8tRVQ.ivnVrrtSMEyeUNhgogx4yDhK5VY');
