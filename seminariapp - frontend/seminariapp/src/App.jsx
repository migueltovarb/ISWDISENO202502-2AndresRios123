import PropTypes from 'prop-types'
import Navbar from './components/Navbar.jsx'
import './App.css'

const AppLayout = ({ children }) => {
  return (
    <div className="app-shell">
      <Navbar />
      <main className="app-content">{children}</main>
    </div>
  )
}

AppLayout.propTypes = {
  children: PropTypes.node,
}

export default AppLayout
