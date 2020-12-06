var http = require('http');
const Discord = require('discord.js');
const fs = require('fs');
const { on } = require('process');

const client = new Discord.Client();

client.once('ready', () => {
    console.log('I\'m online');
});

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

    var userName = args[0];
    if (command === 'move') {
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

    if (command === 'mute') {
        client.guilds.fetch('784718151219937290').then((guild) => {
            var members = guild.members;
            for (const [key, value] of members.cache) {
                if (value.nickname === userName) {
                    members.fetch(value.id).then((user) => {
                        user.edit({mute:1});
                    })
                }
            }
        })
    }

    if (command === 'unmute') {
        client.guilds.fetch('784718151219937290').then((guild) => {
            var members = guild.members;
            for (const [key, value] of members.cache) {
                if (value.nickname === userName) {
                    members.fetch(value.id).then((user) => {
                        user.edit({mute:0});
                    })
                }
            }
        })
    }

    if (command === 'deafen') {
        client.guilds.fetch('784718151219937290').then((guild) => {
            var members = guild.members;
            for (const [key, value] of members.cache) {
                if (value.nickname === userName) {
                    members.fetch(value.id).then((user) => {
                        user.edit({deaf:1});
                    })
                }
            }
        })
    }

    if (command === 'undeafen') {
        client.guilds.fetch('784718151219937290').then((guild) => {
            var members = guild.members;
            for (const [key, value] of members.cache) {
                if (value.nickname === userName) {
                    members.fetch(value.id).then((user) => {
                        user.edit({deaf:0});
                    })
                }
            }
        })
    }

    if (command === 'exit') {
        client.guilds.fetch('784718151219937290').then((guild) => {
            var members = guild.members;
            for (const [key, value] of members.cache) {
                if (value.nickname === userName) {
                    members.fetch(value.id).then((user) => {
                        user.edit({channel_id:null});
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

            // var request = http.request({
            //     port: 8080,
            //     host: '127.0.0.1',
            //     method: 'GET',
            //     path: "/get-rooms-info?name=" + nick + "&newChannel=" + newUserChannel + "&oldChannel=" + oldUserChannel
            // })
            // request.end();

            var request = http.request({
                port: 8080,
                host: '52.51.148.88',
                method: 'GET',
                path: "/get-rooms-info?name=" + nick + "&newChannel=" + newUserChannel + "&oldChannel=" + oldUserChannel
            })
            request.end();

            // request = http.request({
            //     port: 8080,
            //     host: '192.168.100.52',
            //     method: 'GET',
            //     path: "/get-rooms-info?name=" + nick + "&newChannel=" + newUserChannel + "&oldChannel=" + oldUserChannel
            // })
            // request.end();
        })
    }
})


client.login('Nzg0NzExMjg0MzE3ODgwMzIw.X8tRVQ.ivnVrrtSMEyeUNhgogx4yDhK5VY');
