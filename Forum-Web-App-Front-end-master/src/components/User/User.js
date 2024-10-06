import { useParams } from 'react-router-dom';
import UserActivity from './UserActivity';

function User() {
  const { userId } = useParams();

  return (
    <div>
      <h1>User {userId}</h1>
      <UserActivity/>
    </div>
  );
}

export default User;