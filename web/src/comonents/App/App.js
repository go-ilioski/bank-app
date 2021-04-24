import {AuthProvider} from "../../context/AuthContext";
import Login from "../Login/Login";
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import DashBoard from "../DashBoard/DashBoard";
import 'bootstrap/dist/css/bootstrap.min.css';
import ProtectedRoute from "../ProtectedRoute/ProtectedRoute";
import Register from "../Register/Register";
import Main from "../Main/Main";

function App() {
  return (
    <div className="App">
      <AuthProvider>
          <Router>
              <Switch>
                  <Route path="/login" component={Login}/>
                  <Route path="/register" component={Register}/>
                  <ProtectedRoute path="" component={Main}/>
              </Switch>
          </Router>
      </AuthProvider>
    </div>
  );
}

export default App;
