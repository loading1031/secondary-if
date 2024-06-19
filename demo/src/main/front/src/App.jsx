import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import NavBar from './components/NavBar/NavBar'
import Footer from './components/Footer/Footer'

function App() {
  const [count, setCount] = useState(0)

  return (
   <div>
    <NavBar/>
    <Footer/>
   </div>
  )
}

export default App
