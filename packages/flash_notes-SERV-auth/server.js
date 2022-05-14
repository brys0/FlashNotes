const express = require('express')
const app = express()
app.use(express.json())
app.use(
  express.urlencoded({
    extended: true,
  }),
)
app.enable("trust proxy");
require('dotenv').config()
const CLIENT_ID = process.env.CLIENT_ID
const CLIENT_URL = process.env.CLIENT_URL

const port = 3001
// * Routes *
app.get('/api', (req, res) => {
    if (process.env.NODE_ENV === 'production') {
      res.send('Server is up!')
    } else {
      res.json({
        message: 'Server is up!',
        clientUrl: CLIENT_URL,
        clientId: CLIENT_ID,
      })
    }
  })
// This route is just for testing, comment out or remove in production if desired
app.get('/', (req, res) => {
  if (process.env.NODE_ENV === 'production') {
    res.send('Server is up!')
  } else {
    res.json({
      message: 'Server is up!',
      clientUrl: CLIENT_URL,
      clientId: CLIENT_ID,
    })
  }
})

// Main Route, route name can be changed as desired
app.post('/verify-token', (req, res) => {
  console.log(req)
  const CLIENT_ID = process.env.CLIENT_ID

  const { OAuth2Client } = require('google-auth-library')
  const client = new OAuth2Client(CLIENT_ID)

  async function verify() {
    const token = req.body.credential

    const ticket = await client.verifyIdToken({
      idToken: token,
      audience: CLIENT_ID, // Specify the CLIENT_ID of the app that accesses the backend
      // Or, if multiple clients access the backend:
      //[CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3]
    })

    const payload = ticket.getPayload()
    // const userid = payload['sub']

    // If request specified a G Suite domain:
    // const domain = payload['hd']

    res.json({
      email: payload.email,
      email_verified: payload.email_verified,
      picture: payload.picture,
      name: payload.name,
    })
  }

  verify().catch(console.error)
})

app.listen(port, () => {
  console.log(`SignIn Server is listening on port ${port}`)
})
