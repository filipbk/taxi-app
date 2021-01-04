import React, {Component} from "react";
import {authenticationService} from "../utils";
import {CompanyHome, DriverHome} from "./index";
import {CustomerHome} from "../Customer";

class HomePage extends Component {
    interval = null;

    constructor(props) {
        super(props);

        this.state = {
            user: authenticationService.currentUserValue,
            userStatus: null
        }
    }

    render() {
        const {user, userStatus} = this.state;

        if(user.usertype === 'DRIVER') {
            return (<DriverHome status={userStatus} />);
        } else if(user.usertype === 'COMPANY') {
            return (<CompanyHome status={userStatus} />);
        } else if(user.usertype === 'CUSTOMER') {
            return (<CustomerHome status={userStatus} />);
        }

        return (
            <React.Fragment>
                Nazwa użytkownika: {user.username} <br/>
                Typ użytkownika: {user.usertype}
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ornare odio ut magna pharetra eleifend. Praesent vehicula, turpis sed auctor dignissim, dui risus hendrerit nisl, venenatis maximus justo mi in odio. Sed porta, lorem tempus viverra tincidunt, magna augue aliquam ante, sed suscipit orci ante ac odio. Sed convallis tortor sit amet dictum finibus. Donec interdum, ipsum nec dictum lobortis, ipsum ligula elementum tellus, eu vestibulum enim tortor quis ligula. Donec malesuada ipsum sed felis maximus, in suscipit nibh tincidunt. Donec dignissim a risus tempus sodales. Suspendisse mattis id metus non fermentum. Fusce lobortis non urna at pulvinar. Nam tempor bibendum tristique. Aliquam aliquet libero aliquam velit vestibulum ornare. Nunc varius ligula rutrum, pretium enim nec, lacinia metus.

                Nunc enim enim, fermentum a arcu a, hendrerit dictum libero. In eu turpis dignissim, tincidunt erat ut, suscipit lorem. Phasellus efficitur enim arcu, tempor pellentesque nibh dignissim at. Interdum et malesuada fames ac ante ipsum primis in faucibus. Fusce in facilisis ex. Etiam ultricies, urna vitae mattis tincidunt, eros risus maximus nulla, vitae tincidunt lacus orci quis leo. Aliquam non erat pellentesque, pharetra nisi convallis, finibus dolor.

                Aliquam erat volutpat. Vestibulum maximus tincidunt efficitur. Aenean gravida, lacus eu dignissim varius, risus diam ornare nisl, vel placerat massa augue ut urna. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec sodales eros id tincidunt tincidunt. Phasellus gravida arcu a risus molestie pretium. In velit sem, accumsan eget tempor eu, tincidunt nec nisi. Morbi non varius libero. Sed tincidunt odio in lectus sagittis, vitae dignissim magna mollis. Cras accumsan lorem eget metus porta, eget vehicula leo ullamcorper.

                Cras eleifend et urna ut mollis. Suspendisse tincidunt semper lacus ac gravida. Pellentesque eget ligula at augue tristique gravida. Fusce pulvinar turpis elit, vel sagittis libero tempor ac. Aliquam pulvinar ut velit nec convallis. Sed sed arcu gravida, malesuada dolor et, rhoncus eros. Cras purus nisl, cursus eget justo non, molestie sodales lectus. Nullam eros nisl, imperdiet vitae dolor eu, aliquet dignissim justo. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras nec felis malesuada, rhoncus orci quis, luctus ligula.

                Donec sodales, est eget tempus sollicitudin, mi nisi congue turpis, vel malesuada magna dui eget dui. Nunc pretium sapien a malesuada commodo. Aliquam cursus mauris vel diam fringilla scelerisque. Curabitur in elit vitae risus fermentum scelerisque nec in urna. Curabitur ut erat fermentum, consectetur metus et, mattis purus. Sed condimentum bibendum ipsum in blandit. Nam bibendum dui ornare pellentesque pellentesque. Ut ut dolor at odio porta malesuada. Nulla finibus mauris nec consequat eleifend. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum velit sem, interdum id nibh pretium, dapibus bibendum neque.

                Sed rhoncus ante ipsum, quis vulputate neque ultricies eu. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur imperdiet sed ipsum eget bibendum. Quisque interdum purus eget ipsum pellentesque, non volutpat erat vestibulum. Vestibulum quis auctor nulla. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar, enim in mattis mollis, nisi sapien suscipit sem, in sodales mi ante nec nisi. Etiam ac diam ut justo malesuada semper vitae vitae nisi. Praesent sed velit non sapien condimentum imperdiet ut et libero. Nulla facilisi.

                Suspendisse at nisi non tellus fermentum sagittis. Fusce sit amet viverra turpis. Fusce maximus tincidunt quam. Integer tristique eget neque eget sagittis. Quisque eu porttitor justo. Phasellus tempor iaculis ex eleifend auctor. Pellentesque non erat tempor, hendrerit augue in, aliquet urna. Integer pretium congue velit, ac vehicula sem auctor ut. Nullam pretium nunc ut urna interdum iaculis. Duis eget pulvinar neque, vel mattis leo. Etiam dapibus nisl eu libero placerat pellentesque. Maecenas faucibus urna at ultricies imperdiet. Aenean dignissim neque eget ex volutpat, nec tristique ante hendrerit. Ut sit amet odio finibus, dapibus tellus a, accumsan nisi. Integer condimentum mattis pulvinar. Duis imperdiet sem et nibh rhoncus ullamcorper eget vel sem.
            </React.Fragment>
        )
    }

    componentDidMount() {
        this.interval = setInterval(authenticationService.fetchUserStatus, 10000);

        authenticationService.userStatus.subscribe(x => {
            this.setState({
                userStatus: x
            });
        });
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }
}

export default HomePage;