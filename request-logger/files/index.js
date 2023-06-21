const http = require('http')

let logs = []

const app = http.createServer((req, res) => {

    console.log({
        url: req.url,
        headers: req.headers,
    })

    if (req.url === '/logs') {
        res.setHeader('Content-Type', 'application/json');
        return res.end(JSON.stringify(logs))
    }

    if (req.url === '/reset') {
        return res.end('Logs excluídos!')
    }

    logs.push({
        url: req.url,
        headers: req.headers,
        date: new Date()
    })

    res.end('Hello World')
})

const PORT = process.env.NODE_PORT || 8088

app.listen(PORT, () => console.log(`Listening on ${PORT} port.`))