//
//  H5WebviewController.m
//  WebviewSampleiOS
//
//  Created by Martin on 2024/7/30.
//

#import "H5WebviewController.h"
#import <WebKit/WebKit.h>


@interface H5WebviewController () <WKNavigationDelegate, WKScriptMessageHandler>
@property (weak, nonatomic) WKWebView *webView;
@property (weak, nonatomic) UIView *closeButton;

@end


static NSString *const requestUrl = @"https://news.zephyrona.com/scene?sk=q851d2bccce9a8179&lzdid=88888888-8888-8888-8888-888888888888";
// 是否展示关闭按钮
static bool showCloseButton = true;

@implementation H5WebviewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
    WKUserContentController *controller = [[WKUserContentController alloc] init];

    configuration.userContentController = controller;
    
    WKWebView *webView = [[WKWebView alloc] initWithFrame:self.view.frame configuration:configuration];
    self.webView = webView;
    self.view.backgroundColor = [UIColor whiteColor];
    self.webView.scrollView.backgroundColor = [UIColor whiteColor];
    self.webView.navigationDelegate = self;
    self.webView.allowsBackForwardNavigationGestures = YES;
    [self.view addSubview:self.webView];
    
    NSURL *url = [[NSURL alloc] initWithString: requestUrl];
    NSURLRequest *request = [[NSURLRequest alloc] initWithURL: url
                                                  cachePolicy: NSURLRequestUseProtocolCachePolicy timeoutInterval: 30];
    [self.webView loadRequest:request];
    
    if (showCloseButton) {
        UIView *closeButton = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 30, 30)];
        self.closeButton = closeButton;
        self.closeButton.backgroundColor = [UIColor clearColor];
        [self.view addSubview:self.closeButton];
        
        UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(closeButtonTapped)];
        [self.closeButton addGestureRecognizer:tapGesture];
    }

}

- (void)viewDidLayoutSubviews {
    [super viewDidLayoutSubviews];
    
    CGFloat topInset = self.view.safeAreaInsets.top;
    self.webView.frame = CGRectMake(0,
                                    topInset,
                                    self.view.bounds.size.width,
                                    self.view.bounds.size.height);
    
    if (showCloseButton) {
        self.closeButton.frame = CGRectMake(20, topInset + 20, 30, 30);
        [self drawCloseButtonInView:self.closeButton];
    }
    
}

- (void)closeButtonTapped {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)drawCloseButtonInView:(UIView *)view {
    UIGraphicsBeginImageContext(view.bounds.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    
    UIColor *circleColor = [[UIColor blackColor] colorWithAlphaComponent:0.5];
    CGContextSetFillColorWithColor(context, circleColor.CGColor);
    CGContextFillEllipseInRect(context, view.bounds);
    
    CGFloat xSize = 15;
    CGFloat padding = (view.bounds.size.width - xSize) / 2;
    CGContextSetStrokeColorWithColor(context, [UIColor whiteColor].CGColor);
    CGContextSetLineWidth(context, 2.0);
    
    CGContextMoveToPoint(context, padding, padding);
    CGContextAddLineToPoint(context, view.bounds.size.width - padding, view.bounds.size.height - padding);
    
    CGContextMoveToPoint(context, view.bounds.size.width - padding, padding);
    CGContextAddLineToPoint(context, padding, view.bounds.size.height - padding);
    
    CGContextStrokePath(context);
    
    view.layer.contents = (id)UIGraphicsGetImageFromCurrentImageContext().CGImage;
    
    UIGraphicsEndImageContext();
}

#pragma mark - WKWebView WKNavigationDelegate
- (void)webView:(WKWebView *)webView didFinishNavigation:(WKNavigation *)navigation {
    
}


#pragma mark - WKWebView WKNavigationDelegate
- (void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {
    
    NSURL *url = [navigationAction.request URL];
    if (![url.scheme isEqual:@"http"] && ![url.scheme isEqual:@"https"]) {
        if ([[UIDevice currentDevice].systemVersion floatValue] <= 10.0) {
            [[UIApplication sharedApplication] openURL:url];
        } else {
            [[UIApplication sharedApplication] openURL:url options:@{} completionHandler:nil];
        }
    }
    
    decisionHandler(WKNavigationActionPolicyAllow);
}

#pragma mark - WKScriptMessageHandler
- (void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message {
    
}

@end
