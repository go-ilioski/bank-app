import {AuthProvider} from "../../context/AuthContext";
import Login from "../Login/Login";
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import DashBoard from "../DashBoard/DashBoard";
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
    <div className="App">
      <AuthProvider>
          <Router>
              <Switch>
                  <Route path="/login" component={Login}/>
                  <Route path="" component={DashBoard}/>
              </Switch>
          </Router>
      </AuthProvider>
    </div>
  );
}

export default App;
